package br.com.clinic.service.interfaces;

import br.com.clinic.model.enums.FormaPagamento;
import br.com.clinic.model.classes.financeiro.Pagamento;

import java.time.LocalDate;

/**
 *
 * @author Raul Pablo
 * */
public interface IFinanceiroService extends CrudService<Pagamento, Long>{
    Pagamento gerarPagamento(double valor, FormaPagamento formaPagamento);
    double calcularSaldoPeriodo(LocalDate inicio, LocalDate fim);
}
