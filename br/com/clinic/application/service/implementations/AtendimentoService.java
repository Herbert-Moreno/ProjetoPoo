package br.com.clinic.application.service.implementations;

//region Import Exception
import br.com.clinic.domain.enums.FormaPagamento;
import br.com.clinic.domain.enums.StatusAtendimento;
import br.com.clinic.domain.execeptions.AtendimentoNaoEncontradoException;
import br.com.clinic.domain.execeptions.PacienteNaoEncontradoException;
import br.com.clinic.domain.execeptions.ProfissionalNaoEncontradoException;
//endregion

//region Import Model
import br.com.clinic.domain.model.financeiro.Pagamento;
import br.com.clinic.domain.model.procedimento.Procedimento;
import br.com.clinic.domain.model.pessoa.Paciente;
import br.com.clinic.domain.model.pessoa.Profissional;
import br.com.clinic.domain.model.atendimento.Atendimento;
//endregion

//region Import Interfaces repository
import br.com.clinic.domain.repository.IAtendimentoRepository;
//endregion

//region Import Interfaces service
import br.com.clinic.application.service.interfaces.IAtendimentoService;
import br.com.clinic.application.service.interfaces.IFinanceiroService;
import br.com.clinic.application.service.interfaces.IPacienteService;
import br.com.clinic.application.service.interfaces.IProfissionalService;
//endregion

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


/**
 * Serviço central do sistema. Orquestra múltiplos domínios (Pacientes, Profissionais,
 * Procedimentos e Finanças) para realizar agendamentos e fechamentos de contas.
 * @author Raul Pablo
 */
/* Classe: AtendimentoService */
public class AtendimentoService implements IAtendimentoService {

    //region Campos
    private final IAtendimentoRepository atendimentoRepository;

    private final IPacienteService pacienteService;
    private final IProfissionalService profissionalService;
    private final IFinanceiroService financeiroService;
    //endregion

    public AtendimentoService(IAtendimentoRepository atendimentoRepository,
                              IPacienteService pacienteService,
                              IProfissionalService profissionalService,
                              IFinanceiroService financeiroService)
    {
        this.atendimentoRepository = atendimentoRepository;

        this.pacienteService = pacienteService;
        this.profissionalService = profissionalService;
        this.financeiroService = financeiroService;
    }

    /**
     * Realiza o agendamento prévio de um atendimento vinculando um paciente, um profissional e procedimentos.
     * * @param pacienteId O ID do paciente que receberá o atendimento.
     * @param profisionalId O ID do profissional responsável pelo procedimento.
     * @param procedimentos A lista de procedimentos que serão executados.
     * @param data A data agendada para a sessão.
     * @return O objeto Atendimento instanciado e salvo com o status inicial de AGENDADO.
     * @throws PacienteNaoEncontradoException Se o pacienteId não existir.
     * @throws ProfissionalNaoEncontradoException Se o profissionalId não existir.
     * @throws IllegalArgumentException Se a lista de procedimentos estiver nula ou vazia.
     */
    @Override
    public Atendimento criarAtendimento(Long pacienteId, Long profisionalId, List<Procedimento> procedimentos, LocalDate data)
    {
         Paciente paciente = pacienteService.buscarPorId(pacienteId)
                .orElseThrow(() -> new PacienteNaoEncontradoException(pacienteId));

        Profissional profissional = profissionalService.buscarPorId(profisionalId)
                .orElseThrow(() -> new ProfissionalNaoEncontradoException(profisionalId));

        Atendimento atendimento = new Atendimento(paciente, profissional, data, procedimentos);

        return atendimentoRepository.salvar(atendimento);
    }

    /**
     * Finaliza o atendimento calculando os valores, aplicando descontos/acréscimos de acordo com a forma de pagamento e alterando os status internos.
     * @param atendimentoId O ID do atendimento que está sendo concluído.
     * @param formaPagamento A modalidade financeira escolhida para quitar o atendimento.
     * @return O atendimento atualizado com status REALIZADO e com o Pagamento PAGO associado.
     * @throws AtendimentoNaoEncontradoException Se o atendimentoId informado for inválido.
     * @throws IllegalStateException Se o atendimento já estiver finalizado (status diferente de AGENDADO) ou se não contiver procedimentos.
     */
    @Override
    public Atendimento finalizarAtendimento(Long atendimentoId, FormaPagamento formaPagamento) {

        Atendimento atendimento = atendimentoRepository.buscarPorId(atendimentoId)
                .orElseThrow(() -> new AtendimentoNaoEncontradoException(atendimentoId));

        if (atendimento.getStatus() != StatusAtendimento.AGENDADO) {
            throw new IllegalStateException("Apenas atendimentos agendados podem ser finalizados. Status atual: " + atendimento.getStatus());
        }

        double valorTotal = atendimento.calcularTotalAtendimento();
        //String descricao = "Pgto Atendimento #" + atendimento.getId() + " - Cliente: " + atendimento.getPaciente().getNome();

        Pagamento pagamento = financeiroService.gerarPagamento(valorTotal, formaPagamento);
        pagamento.confirmarPagamento();

        // salva no repositorio de atendimento
        atendimento.finalizar(pagamento);

        return atendimentoRepository.salvar(atendimento);
    }

    @Override
    public Atendimento cancelarAtendimento(Long atendimentoId) {
        Atendimento atendimento = atendimentoRepository.buscarPorId(atendimentoId)
                .orElseThrow(() -> new AtendimentoNaoEncontradoException(atendimentoId));
        atendimento.cancelar();

        return atendimentoRepository.salvar(atendimento);
    }

    /**
     * Salva ou atualiza o estado de um objeto Atendimento de forma direta.
     * @param atendimento O atendimento a ser persistido.
     * @return O atendimento salvo.
     * @throws IllegalArgumentException Se a entidade de atendimento fornecida for nula.
     */
    @Override
    public Atendimento salvar(Atendimento atendimento){
        // proteçao para chamadas diretas via interface
        if (atendimento == null) {
            throw new IllegalArgumentException("O atendimento a ser salvo não pode ser nulo.");
        }
        return atendimentoRepository.salvar(atendimento);
    }

    /**
     * Remove um atendimento do histórico com base no seu ID.
     * @param id O identificador único do atendimento.
     * @throws AtendimentoNaoEncontradoException Se o atendimento não for localizado.
     */
    @Override
    public void deletar(Long id) {
        Atendimento atendimento = atendimentoRepository.buscarPorId(id)
                .orElseThrow(() -> new AtendimentoNaoEncontradoException(id));

        if (atendimento.getStatus() == StatusAtendimento.REALIZADO) {
            throw new IllegalStateException("Não é possível deletar um atendimento já realizado e pago. " +
                    "O registro deve ser mantido para consistência financeira.");
        }
        atendimentoRepository.deletar(id);
    }

    /**
     * Recupera todos os atendimentos agendados ou realizados em uma data específica.
     * @param data A data que servirá como filtro para a busca.
     * @return Lista contendo os atendimentos daquele dia.
     * @throws IllegalArgumentException Se a data informada para a busca for nula.
     */
    @Override
    public List<Atendimento> buscarPorData(LocalDate data) {
        if (data == null) {
            throw new IllegalArgumentException("A data de busca não pode ser nula.");
        }
        return atendimentoRepository.buscarData(data);
    }

    /**
     * Localiza um atendimento pelo seu identificador único.
     * @param id O identificador único do atendimento.
     * @return Um Optional contendo o atendimento se encontrado.
     */
    @Override
    public Optional<Atendimento> buscarPorId(Long id) {
        return atendimentoRepository.buscarPorId(id);
    }

    /**
     * Retorna a listagem total de atendimentos gravados no sistema.
     * @return Lista contendo todos os atendimentos.
     */
    @Override
    public List<Atendimento> listarTodos() {
        return atendimentoRepository.listarTodos();
    }

}
