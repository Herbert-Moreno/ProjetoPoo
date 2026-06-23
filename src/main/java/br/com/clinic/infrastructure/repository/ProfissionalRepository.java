package br.com.clinic.infrastructure.repository;

import br.com.clinic.domain.model.pessoa.Profissional;
import br.com.clinic.domain.repository.IProfissionalRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class ProfissionalRepository implements IProfissionalRepository {
    private final List<Profissional> profissionais = new ArrayList<>();
    private final AtomicLong sequenciaId = new AtomicLong(0);

    @Override
    public Profissional salvar(Profissional profissional) {
        if (profissional == null) {
            throw new IllegalArgumentException("Profissional não pode ser nulo.");
        }

        if (profissional.getId() == null) {
            profissional.setId(sequenciaId.incrementAndGet());
            profissionais.add(profissional);
        } else {
            int indice = buscarIndicePorId(profissional.getId());
            if (indice >= 0) {
                profissionais.set(indice, profissional);
            } else {
                profissionais.add(profissional);
            }
        }

        return profissional;
    }

    @Override
    public Optional<Profissional> buscarPorId(Long id) {
        if (id == null) {
            return Optional.empty();
        }

        return profissionais.stream()
                .filter(profissional -> id.equals(profissional.getId()))
                .findFirst();
    }

    @Override
    public void deletar(Long id) {
        buscarPorId(id).ifPresent(profissionais::remove);
    }

    @Override
    public List<Profissional> buscarPorEspecialidade(String especialidade) {
        if (especialidade == null || especialidade.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String termo = especialidade.trim().toLowerCase();
        return profissionais.stream()
                .filter(profissional -> profissional.getEspecialidade() != null
                        && profissional.getEspecialidade().toLowerCase().contains(termo))
                .collect(Collectors.toList());
    }

    @Override
    public List<Profissional> listarTodos() {
        return new ArrayList<>(profissionais);
    }

    private int buscarIndicePorId(Long id) {
        for (int i = 0; i < profissionais.size(); i++) {
            if (id.equals(profissionais.get(i).getId())) {
                return i;
            }
        }
        return -1;
    }
}