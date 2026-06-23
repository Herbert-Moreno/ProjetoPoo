package br.com.clinic.infrastructure.repository;

import br.com.clinic.domain.model.pessoa.Paciente;
import br.com.clinic.domain.repository.IPacienteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class PacienteRepository implements IPacienteRepository {
    private final List<Paciente> pacientes = new ArrayList<>();
    private final AtomicLong sequenciaId = new AtomicLong(0);

    @Override
    public Paciente salvar(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("Paciente não pode ser nulo.");
        }

        if (paciente.getId() == null) {
            paciente.setId(sequenciaId.incrementAndGet());
            pacientes.add(paciente);
        } else {
            int indice = buscarIndicePorId(paciente.getId());
            if (indice >= 0) {
                pacientes.set(indice, paciente);
            } else {
                pacientes.add(paciente);
            }
        }

        return paciente;
    }

    @Override
    public Optional<Paciente> buscarPorId(Long id) {
        if (id == null) {
            return Optional.empty();
        }

        return pacientes.stream()
                .filter(paciente -> id.equals(paciente.getId()))
                .findFirst();
    }

    @Override
    public void deletar(Long id) {
        buscarPorId(id).ifPresent(pacientes::remove);
    }

    @Override
    public Optional<Paciente> buscarPorCpf(String cpf) {
        if (cpf == null) {
            return Optional.empty();
        }

        String cpfNormalizado = cpf.replaceAll("\\D", "");
        return pacientes.stream()
                .filter(paciente -> paciente.getCpf() != null
                        && cpfNormalizado.equals(paciente.getCpf().replaceAll("\\D", "")))
                .findFirst();
    }

    @Override
    public List<Paciente> buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String termo = nome.trim().toLowerCase();
        return pacientes.stream()
                .filter(paciente -> paciente.getNome() != null
                        && paciente.getNome().toLowerCase().contains(termo))
                .collect(Collectors.toList());
    }

    @Override
    public List<Paciente> listarTodos() {
        return new ArrayList<>(pacientes);
    }

    private int buscarIndicePorId(Long id) {
        for (int i = 0; i < pacientes.size(); i++) {
            if (id.equals(pacientes.get(i).getId())) {
                return i;
            }
        }
        return -1;
    }
}