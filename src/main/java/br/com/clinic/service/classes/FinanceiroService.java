package br.com.clinic.service.classes;

import br.com.clinic.model.classes.financeiro.Pagamento;
import br.com.clinic.model.classes.pessoa.Paciente;
import br.com.clinic.model.enums.FormaPagamento;
import br.com.clinic.service.interfaces.IFinanceiroService;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private final List<Pagamento> db = new ArrayList<>();

    /**
     * Valida as restrições monetárias e temporais de um pagamento antes de salvá-lo.
     * @param pagamento O lançamento financeiro a ser registrado.
     * @return O pagamento salvo e integrado ao fluxo de caixa.
     * @throws IllegalArgumentException Se o valor do lançamento for menor ou igual a zero, ou se a data de pagamento for nula.
     */
    @Override
    public void salvar(Pagamento pagamento) {
        if (pagamento.getValor() <= 0) {
            throw new IllegalArgumentException("O valor do lançamento deve ser maior que zero.");
        }
        if (pagamento.getData_pagamento() == null) {
            throw new IllegalArgumentException("A data do lançamento é obrigatória.");
        }

        this.db.add(pagamento);
    }

    /**
     * Remove um registro financeiro do sistema.
     * @param id O identificador único do lançamento financeiro.
     */
    @Override
    public void deletar(Long id) {
        Optional<Pagamento> pagamento = this.buscarPorId(id);
        pagamento.ifPresent(value -> this.db.remove(value));
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

        return this.listarTodos().stream()
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
        this.salvar(pagamento);
        return pagamento;
    }

    /**
     * Busca um lançamento de pagamento específico através do seu ID.
     * @param id O identificador único do pagamento.
     * @return Um Optional contendo o pagamento caso ele seja localizado.
     */
    @Override
    public Optional<Pagamento> buscarPorId(Long id) {
        for (Pagamento p : this.db) {
            if (p.getId_pagamento() == id) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    /**
     * Retorna o histórico completo de todas as transações de pagamento da clínica.
     * @return Lista com todos os pagamentos armazenados.
     */
    @Override
    public List<Pagamento> listarTodos() {
        return this.db;
    }
}