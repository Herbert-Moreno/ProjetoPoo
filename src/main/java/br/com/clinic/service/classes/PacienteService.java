package br.com.clinic.service.classes;

import br.com.clinic.service.interfaces.IPacienteService;
import br.com.clinic.model.classes.pessoa.Paciente;

import br.com.clinic.service.utils.ValidadorUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável por gerenciar as regras de negócio associadas ao cadastro e consulta de pacientes.
 * @author Romulo Lopes
 */

/* Classe: PacienteService */
public class PacienteService implements IPacienteService {
    List<Paciente> pacientes = new ArrayList<>();
    /**
     * Valida os dados obrigatórios e persiste ou atualiza um paciente.
     * @param paciente O objeto Paciente com os dados a serem validados e salvos.
     * @return O paciente salvo e integrado com o repositório.
     */
    @Override
    public void salvar(Paciente paciente) {
        ValidadorUtils.validarTextoObrigatorio(paciente.getNome(), "O nome do cliente e obrigatório.");
        ValidadorUtils.validarCpf(paciente.getCpf());
        this.pacientes.add(paciente);
    }

    /**
     * Remove um paciente do sistema com base no seu ID após verificar sua existência.
     * @param id O identificador único do paciente.
     */
    @Override
    public void deletar(Long id) {
        Optional<Paciente> paciente = this.buscarPorId(id);
        paciente.ifPresent(value -> this.pacientes.remove(value));
    }

    /**
     * Busca um paciente pelo seu ID.
     * @param id O identificador único do paciente.
     * @return Um Optional contendo o paciente se encontrado.
     */
    @Override
    public Optional<Paciente> buscarPorId(Long id) {
        for (Paciente item : this.pacientes) {
            if (item.getId() == id) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

    /**
     * Filtra pacientes pelo nome exigindo um limite mínimo de caracteres.
     * @param nome O nome ou parte do nome para pesquisa.
     * @return Lista de pacientes que atendem ao critério de busca.
     */
    @Override
    public Optional<Paciente> buscarPorNome(String nome) {
        List<Paciente> ls = new ArrayList<>();
        ValidadorUtils.validarTamanhoMinimo(nome, 3, "Valor inserido abaixo do minimo nescessario");
        for (Paciente p : this.pacientes) {
            if (p.getNome().equals(nome)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    /**
     * Lista todos os pacientes cadastrados.
     * @return Lista contendo todos os pacientes da base de dados.
     */
    @Override
    public List<Paciente> listarTodos() {
        return this.pacientes;
    }

}
