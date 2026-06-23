package br.com.clinic.infrastructure.repository;

import br.com.clinic.domain.model.atendimento.Atendimento;
import br.com.clinic.domain.repository.IAtendimentoRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class AtendimentoRepository implements IAtendimentoRepository {
    private final List<Atendimento> atendimentos = new ArrayList<>();
    private final AtomicLong sequenciaId = new AtomicLong(0);

    @Override
    public Atendimento salvar(Atendimento atendimento) {
        if (atendimento == null) {
            throw new IllegalArgumentException("Atendimento não pode ser nulo.");
        }

        if (atendimento.getId() == 0) {
            atendimento.setId(sequenciaId.incrementAndGet());
            atendimentos.add(atendimento);
        } else {
            int indice = buscarIndicePorId(atendimento.getId());
            if (indice >= 0) {
                atendimentos.set(indice, atendimento);
            } else {
                atendimentos.add(atendimento);
            }
        }

        return atendimento;
    }

    @Override
    public Optional<Atendimento> buscarPorId(Long id) {
        if (id == null) {
            return Optional.empty();
        }

        return atendimentos.stream()
                .filter(atendimento -> id.equals(atendimento.getId()))
                .findFirst();
    }

    @Override
    public void deletar(Long id) {
        buscarPorId(id).ifPresent(atendimentos::remove);
    }

    @Override
    public List<Atendimento> buscarData(LocalDate data) {
        if (data == null) {
            throw new IllegalArgumentException("A data de busca não pode ser nula.");
        }

        return atendimentos.stream()
                .filter(atendimento -> data.equals(atendimento.getData()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Atendimento> listarTodos() {
        return new ArrayList<>(atendimentos);
    }

    private int buscarIndicePorId(Long id) {
        for (int i = 0; i < atendimentos.size(); i++) {
            if (id.equals(atendimentos.get(i).getId())) {
                return i;
            }
        }
        return -1;
    }
}