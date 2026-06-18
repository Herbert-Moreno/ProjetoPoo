package br.com.clinic.application.service.interfaces;

import br.com.clinic.domain.model.pessoa.Paciente;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Romulo Lopes
 * */
public interface IPacienteService extends CrudService<Paciente, Long> {
    Optional<Paciente> buscarPorCpf(String cpf);
    List<Paciente> buscarPorNome(String nome);
}
