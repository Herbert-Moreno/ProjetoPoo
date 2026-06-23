package br.com.clinic.application.service.interfaces;

import br.com.clinic.domain.enums.FormaPagamento;
import br.com.clinic.domain.model.atendimento.Atendimento;
import br.com.clinic.domain.model.procedimento.Procedimento;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Raul Pablo
 * */
public interface IAtendimentoService extends CrudService<Atendimento, Long> {

    Atendimento criarAtendimento(
            Long clienteId,
            Long profisionalId,
            List<Procedimento> procedimentos,
            LocalDate data
    );

    Atendimento finalizarAtendimento(Long atendimentoId, FormaPagamento formaPagamento);
    Atendimento cancelarAtendimento(Long atendimentoId);

    List<Atendimento> buscarPorData(LocalDate data);
}
