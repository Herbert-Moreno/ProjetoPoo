/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.clinic.model.classes.financeiro;

import br.com.clinic.model.enums.FormaPagamento;
import br.com.clinic.model.enums.StatusPagamento;

import java.time.LocalDate;

/**
 *
 * @author bianca
 */
public class Pagamento {
    private int id_pagamento;
    private double valor;
    private LocalDate data_pagamento;
    private FormaPagamento formapagamento;
    private StatusPagamento status;
    private double valorFinal;

    public Pagamento(int id_pagamento, double valor, FormaPagamento formapagamento) {
        this.id_pagamento = id_pagamento;
        this.valor = valor;
        this.data_pagamento = null;
        this.formapagamento = formapagamento;
        this.status = StatusPagamento.PENDENTE;
        this.valorFinal = calcularValorFinal();
    }

    public Pagamento(double valor, FormaPagamento formapagamento) {
        this.valor = valor;
        this.data_pagamento = null;
        this.formapagamento = formapagamento;
        this.status = StatusPagamento.PENDENTE;
        this.valorFinal = calcularValorFinal();
    }

    public int getId_pagamento() {
        return id_pagamento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDate getData_pagamento() {
        return data_pagamento;
    }

    public FormaPagamento getFormapagamento() {
        return formapagamento;
    }

    public void setFormapagamento(FormaPagamento formapagamento) {
        this.formapagamento = formapagamento;
        this.valorFinal = calcularValorFinal();
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public void setStatus(StatusPagamento status) {
        this.status = status;
    }
    
    public double getValorFinal(){
        return valorFinal;
    }

    @Override
    public String toString() {
        return "Pagamento{\n" + "id_pagamento = " + id_pagamento + 
                "\nvalorFinal = " + valorFinal + 
                "\ndata_pagamento = " + data_pagamento + 
                "\nformapagamento = " + formapagamento.getDescricao() + 
                "\nstatus = " + status + '}';
    }
    
    public double calcularValorFinal(){
        switch(formapagamento){
            case PIX -> {
                return valor;
            }
            case DINHEIRO -> {
                return valor * 0.95;
            }
            case CARTAO_CREDITO -> {
                return valor * 1.05;
            }
            case CARTAO_DEBITO -> {
                return valor;
            }
            default -> {
                return valor;
            }
            
        }
    }
    
    public void confirmarPagamento(){
        this.status = StatusPagamento.PAGO;
        this.data_pagamento = LocalDate.now();
    }
    
    public void cancelarPagamento(){
        this.status = StatusPagamento.CANCELADO;
    }
}
