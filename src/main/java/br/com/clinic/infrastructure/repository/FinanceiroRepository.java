package br.com.clinic.infrastructure.repository;

import br.com.clinic.domain.model.financeiro.Pagamento;
import br.com.clinic.domain.repository.IFinanceiroRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FinanceiroRepository implements IFinanceiroRepository {
    private final List<Pagamento> pagamentos = new ArrayList<>();
    private final AtomicLong sequenciaId = new AtomicLong(0);

    @Override
    public Pagamento salvar(Pagamento pagamento) {
        if (pagamento == null) {
            throw new IllegalArgumentException("Pagamento não pode ser nulo.");
        }

        if (pagamento.getId() == null) {
            pagamento.setId(sequenciaId.incrementAndGet());
            pagamentos.add(pagamento);
        } else {
            int indice = buscarIndicePorId(pagamento.getId());
            if (indice >= 0) {
                pagamentos.set(indice, pagamento);
            } else {
                pagamentos.add(pagamento);
            }
        }

        return pagamento;
    }

    @Override
    public Optional<Pagamento> buscarPorId(Long id) {
        if (id == null) {
            return Optional.empty();
        }

        return pagamentos.stream()
                .filter(p -> id.equals(p.getId()))
                .findFirst();
    }

    @Override
    public void deletar(Long id) {
        buscarPorId(id).ifPresent(pagamentos::remove);
    }

    @Override
    public List<Pagamento> listarTodos() {
        return new ArrayList<>(pagamentos);
    }

    private int buscarIndicePorId(Long id) {
        for (int i = 0; i < pagamentos.size(); i++) {
            if (id.equals(pagamentos.get(i).getId())) {
                return i;
            }
        }
        return -1;
    }
}
