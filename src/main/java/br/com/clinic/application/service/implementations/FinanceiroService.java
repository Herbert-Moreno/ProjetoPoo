package br.com.clinic.application.service.implementations;

import br.com.clinic.application.service.interfaces.IFinanceiroService;
import br.com.clinic.domain.enums.FormaPagamento;
import br.com.clinic.domain.execeptions.PagamentoNaoEncontradoException;
import br.com.clinic.domain.model.financeiro.Pagamento;
import br.com.clinic.domain.repository.IFinanceiroRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pelo controle de fluxo de caixa,
 * lançamentos de pagamentos e balanços financeiros da clínica.
 *
 * @author Raul Pablo
 */

/* Classe: FinanceiroService */
public class FinanceiroService implements IFinanceiroService {

    private final IFinanceiroRepository financeiroRepository;

    public FinanceiroService(IFinanceiroRepository financeiroRepository) {
        this.financeiroRepository = financeiroRepository;
    }

    /**
     * Valida as restrições monetárias e temporais de um pagamento antes de salvá-lo.
     * @param pagamento O lançamento financeiro a ser registrado.
     * @return O pagamento salvo e integrado ao fluxo de caixa.
     * @throws IllegalArgumentException Se o valor do lançamento for menor ou igual a zero, ou se a data de pagamento for nula.
     */
    @Override
    public Pagamento salvar(Pagamento pagamento) {
        //ValidadorUtils.validarTextoObrigatorio(pagamento.getDescricao(),"A descrição do lançamento é obrigatória.");

        if (pagamento.getValor() <= 0) {
            throw new IllegalArgumentException("O valor do lançamento deve ser maior que zero.");
        }
        if (pagamento.getData_pagamento() == null) {
            throw new IllegalArgumentException("A data do lançamento é obrigatória.");
        }

        return financeiroRepository.salvar(pagamento);
    }

    /**
     * Remove um registro financeiro do sistema.
     * @param id O identificador único do lançamento financeiro.
     * @throws PagamentoNaoEncontradoException Se o registro financeiro correspondente ao ID não existir.
     */
    @Override
    public void deletar(Long id) {
        financeiroRepository.buscarPorId(id)
                .orElseThrow(() -> new PagamentoNaoEncontradoException(id));

        financeiroRepository.deletar(id);
    }

    /**
     * Calcula o faturamento total bruto obtido dentro de um intervalo fechado de datas.
     * @param inicio A data inicial do período de consulta.
     * @param fim A data final do período de consulta.
     * @return A soma de todos os pagamentos realizados no período informado.
     * @throws IllegalArgumentException Se qualquer uma das datas for nula, ou se a data de início for posterior à data de fim.
     */
    @Override
    public double calcularSaldoPeriodo(LocalDate inicio, LocalDate fim) {
        if (inicio == null || fim == null || inicio.isAfter(fim)) {
            throw new IllegalArgumentException("Período de datas invalido para consulta.");
        }

        return financeiroRepository.listarTodos().stream()
                .filter(p -> !p.getData_pagamento().isBefore(inicio) && !p.getData_pagamento().isAfter(fim))
                .mapToDouble(Pagamento::getValor)
                .sum();
    }

    @Override
    public Pagamento gerarPagamento(double valor, FormaPagamento formaPagamento) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do lançamento deve ser maior que zero.");
        }
        if (formaPagamento == null) {
            throw new IllegalArgumentException("A forma de pagamento deve ser informada.");
        }

        Pagamento pagamento = new Pagamento(valor, formaPagamento);
        pagamento.confirmarPagamento();
        return financeiroRepository.salvar(pagamento);
    }

    /**
     * Busca um lançamento de pagamento específico através do seu ID.
     * @param id O identificador único do pagamento.
     * @return Um Optional contendo o pagamento caso ele seja localizado.
     */
    @Override
    public Optional<Pagamento> buscarPorId(Long id) {
        return financeiroRepository.buscarPorId(id);
    }

    /**
     * Retorna o histórico completo de todas as transações de pagamento da clínica.
     * @return Lista com todos os pagamentos armazenados.
     */
    @Override
    public List<Pagamento> listarTodos() {
        return financeiroRepository.listarTodos();
    }
}
