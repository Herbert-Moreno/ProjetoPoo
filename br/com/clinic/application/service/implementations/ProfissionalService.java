package br.com.clinic.application.service.implementations;

import br.com.clinic.application.service.interfaces.IProfissionalService;
import br.com.clinic.application.utils.ValidadorUtils;
import br.com.clinic.domain.execeptions.ProfissionalNaoEncontradoException;
import br.com.clinic.domain.model.pessoa.Profissional;
import br.com.clinic.domain.repository.IProfissionalRepository;

import java.util.List;
import java.util.Optional;

/**
 * Serviço que gerencia o ciclo de vida e os fluxos de consulta dos profissionais de estética da clínica.
 * @author Romulo Lopes
 */

/* Classe: ProfissionalService */
public class ProfissionalService implements IProfissionalService {

    private final IProfissionalRepository profissionalRepository;

    public ProfissionalService(IProfissionalRepository profissionalRepository){
        this.profissionalRepository = profissionalRepository;
    }

    /**
     * Valida os dados obrigatórios e o registro do conselho antes de persistir o profissional.
     * @param profissional O objeto Profissional a ser validado e salvo.
     * @return O profissional persistido com sucesso.
     * @throws IllegalArgumentException Se o nome, CPF ou o registro profissional forem inválidos ou nulos.
     */
    @Override
    public Profissional salvar(Profissional profissional) {
        ValidadorUtils.validarTextoObrigatorio(profissional.getNome(), "O nome do profissional e obrigatório.");
        ValidadorUtils.validarCpf(profissional.getCpf());
        ValidadorUtils.validarTextoObrigatorio(profissional.getRegistroProfissional(), "O registro do conselho profissional e obrigatório.");
        //ValidadorUtils.validarTextoObrigatorio(profissional.getRegistroEspecialidade(), "O registro do conselho profissional e obrigatório.");

        return profissionalRepository.salvar(profissional);
    }

    /**
     * Remove um profissional do sistema após validar sua existência prévia.
     * @param id O identificador único do profissional.
     * @throws ProfissionalNaoEncontradoException Se o profissional não for localizado na base de dados.
     */
    @Override
    public void deletar(Long id) {
        profissionalRepository.buscarPorId(id)
                .orElseThrow(() -> new ProfissionalNaoEncontradoException(id));
        profissionalRepository.deletar(id);
    }

    /**
     * Busca um profissional pelo seu ID.
     * @param id O identificador único do profissional.
     * @return Um Optional contendo o profissional se encontrado.
     */
    @Override
    public Optional<Profissional> buscarPorId(Long id) {
        return profissionalRepository.buscarPorId(id);
    }

    /**
     * Recupera uma lista de profissionais filtrada por uma determinada especialidade.
     * @param especialidade O nome da especialidade médica/estética desejada.
     * @return Lista de profissionais que atuam na especialidade informada.
     * @throws IllegalArgumentException Se a string de especialidade for nula ou vazia.
     */
    @Override
    public List<Profissional> buscarPorEspecialidade(String especialidade) {
        ValidadorUtils.validarTextoObrigatorio(especialidade, "A especialidade informada para a busca nao pode ser vazia.");
        return profissionalRepository.buscarPorEspecialidade(especialidade);
    }

    /**
     * Lista todos os profissionais cadastrados na clínica.
     * @return Lista contendo todos os profissionais da base de dados.
     */
    @Override
    public List<Profissional> listarTodos() {
        return profissionalRepository.listarTodos();
    }
}
