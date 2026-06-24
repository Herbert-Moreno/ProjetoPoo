/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package br.com.clinic.model.enums;

/**
 *
 * @author bianca
 */
public enum FormaPagamento {
   PIX("Pagamento via Pix"),
   DINHEIRO("Pagamento em Dinheiro"),
   CARTAO_CREDITO("Pagamento com Cartão de crédito"),
   CARTAO_DEBITO("Pagamento com Cartão de débito");
   
   private final String descricao;
   
   FormaPagamento(String descricao){
       this.descricao = descricao;
   }
   
   public String getDescricao(){
       return descricao;
   }
}
