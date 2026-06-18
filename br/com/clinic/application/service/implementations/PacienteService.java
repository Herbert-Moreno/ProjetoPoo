package br.com.clinic.application.service.implementations;

import br.com.clinic.application.service.interfaces.IPacienteService;
import br.com.clinic.domain.execeptions.PacienteNaoEncontradoException;
import br.com.clinic.domain.model.pessoa.Paciente;
import br.com.clinic.domain.repository.IPacienteRepository;

import br.com.clinic.application.utils.ValidadorUtils;

import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável por gerenciar as regras de negócio associadas ao cadastro e consulta de pacientes.
 * @author Romulo Lopes
 */

/* Classe: PacienteService */
public class PacienteService implements IPacienteService {

    private final IPacienteRepository pacienteRepository;

    public PacienteService(IPacienteRepository pacienteRepository){
        this.pacienteRepository = pacienteRepository;
    }

    /**
     * Valida os dados obrigatórios e persiste ou atualiza um paciente.
     * @param paciente O objeto Paciente com os dados a serem validados e salvos.
     * @return O paciente salvo e integrado com o repositório.
     * @throws IllegalArgumentException Se o nome for nulo/vazio ou se o CPF for matematicamente inválido.
     */
    @Override
    public Paciente salvar(Paciente paciente) {
        ValidadorUtils.validarTextoObrigatorio(paciente.getNome(), "O nome do cliente é obrigatório.");
        ValidadorUtils.validarCpf(paciente.getCpf());

        return pacienteRepository.salvar(paciente);
    }

    /**
     * Remove um paciente do sistema com base no seu ID após verificar sua existência.
     * @param id O identificador único do paciente.
     * @throws PacienteNaoEncontradoException Se o ID informado não corresponder a nenhum paciente na base de dados.
     */
    @Override
    public void deletar(Long id) {
        pacienteRepository.buscarPorId(id)
                .orElseThrow(() -> new PacienteNaoEncontradoException(id));

        pacienteRepository.deletar(id);
    }

    /**
     * Busca um paciente pelo seu ID.
     * @param id O identificador único do paciente.
     * @return Um Optional contendo o paciente se encontrado.
     */
    @Override
    public Optional<Paciente> buscarPorId(Long id) {
        return pacienteRepository.buscarPorId(id);
    }

    /**
     * Realiza a busca de um paciente validando o formato do CPF fornecido.
     * @param cpf O CPF do paciente a ser buscado (com ou sem formatação).
     * @return Um Optional contendo o paciente se localizado.
     * @throws IllegalArgumentException Se o CPF informado for nulo, vazio ou inválido.
     */
    @Override
    public Optional<Paciente> buscarPorCpf(String cpf) {
        ValidadorUtils.validarCpf(cpf);
        return pacienteRepository.buscarPorCpf(cpf);
    }

    /**
     * Filtra pacientes pelo nome exigindo um limite mínimo de caracteres.
     * @param nome O nome ou parte do nome para pesquisa.
     * @return Lista de pacientes que atendem ao critério de busca.
     * @throws IllegalArgumentException Se o texto for nulo ou possuir menos de 3 caracteres.
     */
    @Override
    public List<Paciente> buscarPorNome(String nome) {
    ValidadorUtils.validarTamanhoMinimo(nome, 3, "Valor inserido abaixo do minimo nescessario");
        return pacienteRepository.buscarPorNome(nome);
    }

    /**
     * Lista todos os pacientes cadastrados.
     * @return Lista contendo todos os pacientes da base de dados.
     */
    @Override
    public List<Paciente> listarTodos() {
        return pacienteRepository.listarTodos();
    }

}
