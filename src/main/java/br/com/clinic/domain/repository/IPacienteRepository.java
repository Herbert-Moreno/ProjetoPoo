package br.com.clinic.domain.repository;

import br.com.clinic.domain.model.pessoa.Paciente;

import java.util.List;
import java.util.Optional;

public interface IPacienteRepository extends BaseRepository<Paciente, Long> {
    List<Paciente> buscarPorNome(String nome);
    Optional<Paciente> buscarPorCpf(String cpf);
}
