package br.com.clinic.application.service.interfaces;

import br.com.clinic.domain.enums.FormaPagamento;
import br.com.clinic.domain.model.financeiro.Pagamento;

import java.time.LocalDate;

/**
 *
 * @author Raul Pablo
 * */
public interface IFinanceiroService extends CrudService<Pagamento, Long>{
    Pagamento gerarPagamento(double valor, FormaPagamento formaPagamento);
    double calcularSaldoPeriodo(LocalDate inicio, LocalDate fim);
}
