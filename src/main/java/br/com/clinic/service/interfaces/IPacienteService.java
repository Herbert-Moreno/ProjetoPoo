package br.com.clinic.service.interfaces;

import br.com.clinic.model.classes.pessoa.Paciente;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Romulo Lopes
 * */
public interface IPacienteService extends CrudService<Paciente, Long> {
    Optional<Paciente> buscarPorNome(String nome);
}
