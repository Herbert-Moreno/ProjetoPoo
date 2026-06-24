package br.com.clinic.service.interfaces;

import br.com.clinic.model.enums.FormaPagamento;
import br.com.clinic.model.classes.atendimento.Atendimento;
import br.com.clinic.model.classes.procedimento.Procedimento;

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
            List<Procedimento> procedimentos
    );

    Atendimento finalizarAtendimento(Long atendimentoId, FormaPagamento formaPagamento);
    Atendimento cancelarAtendimento(Long atendimentoId);

    List<Atendimento> buscarPorData(LocalDate data);
}
