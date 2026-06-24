package br.com.clinic.service.classes;

import br.com.clinic.model.classes.atendimento.Atendimento;
import br.com.clinic.model.classes.financeiro.Pagamento;
import br.com.clinic.model.classes.pessoa.Paciente;
import br.com.clinic.model.classes.pessoa.Profissional;
import br.com.clinic.model.classes.procedimento.Procedimento;

import br.com.clinic.model.enums.FormaPagamento;

import br.com.clinic.model.enums.StatusAtendimento;
import br.com.clinic.service.interfaces.IAtendimentoService;
import br.com.clinic.service.interfaces.IFinanceiroService;
import br.com.clinic.service.interfaces.IPacienteService;
import br.com.clinic.service.interfaces.IProfissionalService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Serviço central do sistema. Orquestra múltiplos domínios (Pacientes, Profissionais,
 * Procedimentos e Finanças) para realizar agendamentos e fechamentos de contas.
 * @author Raul Pablo
 */
/* Classe: AtendimentoService */
public class AtendimentoService implements IAtendimentoService {

    private final List<Atendimento> db = new ArrayList<>();
    private Long idSequencial = 1L;

    private final IPacienteService pacienteService;
    private final IProfissionalService profissionalService;
    private final IFinanceiroService financeiroService;

    public AtendimentoService(IPacienteService pacienteService,
                              IProfissionalService profissionalService,
                              IFinanceiroService financeiroService)
    {
        this.pacienteService = pacienteService;
        this.profissionalService = profissionalService;
        this.financeiroService = financeiroService;
    }

    /**
     * Realiza o agendamento prévio de um atendimento vinculando um paciente, um profissional e procedimentos.
     * * @param pacienteId O ID do paciente que receberá o atendimento.
     * @param profisionalId O ID do profissional responsável pelo procedimento.
     * @param procedimentos A lista de procedimentos que serão executados.
     * @return O objeto Atendimento instanciado e salvo com o status inicial de AGENDADO.
     * @throws IllegalArgumentException Se a lista de procedimentos estiver nula ou vazia.
     */
    @Override
    public Atendimento criarAtendimento(Long pacienteId, Long profisionalId, List<Procedimento> procedimentos) {
        Paciente paciente = pacienteService.buscarPorId(pacienteId)
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado. Id: " + pacienteId));

        Profissional profissional = profissionalService.buscarPorId(profisionalId)
                .orElseThrow(() -> new IllegalArgumentException("Profissional não encontrado. Id: " + pacienteId));

        Atendimento atendimento = new Atendimento(paciente, profissional, procedimentos);
        salvar(atendimento);

        return atendimento;

    }

    /**
     * Finaliza o atendimento calculando os valores, aplicando descontos/acréscimos de acordo com a forma de pagamento e alterando os status internos.
     *
     * @param atendimentoId  O ID do atendimento que está sendo concluído.
     * @param formaPagamento A modalidade financeira escolhida para quitar o atendimento.
     * @return O atendimento atualizado com status REALIZADO e com o Pagamento PAGO associado.
     * @throws IllegalStateException Se o atendimento já estiver finalizado (status diferente de AGENDADO) ou se não contiver procedimentos.
     */
    @Override
    public Atendimento finalizarAtendimento(Long atendimentoId, FormaPagamento formaPagamento) {
        Atendimento atendimento = buscarPorId(atendimentoId)
                .orElseThrow(() -> new IllegalArgumentException("Atendimento não encontrado, ID: " + atendimentoId));

        if (atendimento.getStatus() != StatusAtendimento.AGENDADO) {
            throw new IllegalStateException("Apenas atendimentos agendados podem ser finalizados. Status atual: " + atendimento.getStatus());
        }

        double valorTotal = atendimento.calcularTotalAtendimento();

        Pagamento pagamento = financeiroService.gerarPagamento(valorTotal, formaPagamento);
        pagamento.confirmarPagamento();

        atendimento.finalizar(pagamento);

        return atendimento;
    }

    @Override
    public Atendimento cancelarAtendimento(Long atendimentoId) {
        Atendimento atendimento = buscarPorId(atendimentoId)
                .orElseThrow(() -> new IllegalArgumentException("Atendimento não encontrado, ID: " + atendimentoId));
        atendimento.cancelar();

        return atendimento;
    }

    @Override
    public List<Atendimento> buscarPorData(LocalDate data) {
        return List.of();
    }

    /**
     * Salva ou atualiza o estado de um objeto Atendimento de forma direta.
     * @param atendimento O atendimento a ser persistido.
     * @return O atendimento salvo.
     * @throws IllegalArgumentException Se a entidade de atendimento fornecida for nula.
     */
    @Override
    public void salvar(Atendimento atendimento) {
        // proteçao para chamadas diretas via interface
        if (atendimento == null) {
            throw new IllegalArgumentException("O atendimento a ser salvo não pode ser nulo.");
        }

        if (atendimento.getId() == 0){
            atendimento.setId(idSequencial++);
            db.add(atendimento);
        }else {
            db.removeIf(
                    a -> Objects.equals(a.getId(), atendimento.getId()));
            db.add(atendimento);
        }
    }

    /**
     * Localiza um atendimento pelo seu identificador único.
     * @param id O identificador único do atendimento.
     * @return Um Optional contendo o atendimento se encontrado.
     */
    @Override
    public Optional<Atendimento> buscarPorId(Long id) {
        return db.stream()
                .filter(atendimento -> Objects.equals(atendimento.getId(), id))
                .findFirst();
    }

    /**
     * Retorna a listagem total de atendimentos gravados no sistema.
     * @return Lista contendo todos os atendimentos.
     */
    @Override
    public List<Atendimento> listarTodos() {
        return this.db;
    }

    /**
     * Remove um atendimento do histórico com base no seu ID.
     * @param id O identificador único do atendimento.
     */
    @Override
    public void deletar(Long id) {
        Atendimento atendimento = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Atendimento não encontrado, ID: " + id));

        if (atendimento.getStatus() == StatusAtendimento.REALIZADO) {
            throw new IllegalStateException("Não é possível deletar um atendimento já realizado e pago. " +
                    "\nO registro deve ser mantido para consistência financeira.");
        }
        db.remove(atendimento);
    }
}
