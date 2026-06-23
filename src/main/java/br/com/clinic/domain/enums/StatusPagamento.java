/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package br.com.clinic.domain.enums;

/**
 *
 * @author bianca
 */
public enum StatusPagamento {
   PENDENTE("Pagamento pendente"),
   PAGO("Pagamento realizado"),
   CANCELADO("Pagamento cancelado");
   
   private final String descricao;
   
   StatusPagamento(String descricao){
       this.descricao = descricao;
   }
   
   public String getDescricao(){
       return descricao;
   }
}
