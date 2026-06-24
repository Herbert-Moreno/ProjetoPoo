/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.clinic.model.classes.procedimento;


import br.com.clinic.model.enums.TipoProcedimento;

/**
 *
 * @author bianca
 */
public class Procedimento {
    private int id;
    private TipoProcedimento procedimento;
    private double valor;
    private int duracao;
    private String descricao;

    public Procedimento(int id, TipoProcedimento procedimento, double valor, int duracao, String descricao) {
        this.id = id;
        this.procedimento = procedimento;
        this.valor = valor;
        this.duracao = duracao;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public TipoProcedimento getProcedimento() {
        return procedimento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Procedimento{\n" + "id = " + id + 
                "\nprocedimento = " + procedimento + 
                "\nvalor = " + valor + 
                "\nduracao = " + duracao + 
                "\ndescricao = " + descricao + '}';
    }
    
    
}
