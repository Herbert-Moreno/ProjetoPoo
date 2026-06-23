package br.com.clinic.domain.model.pessoa;


import br.com.clinic.domain.enums.SexoEnum;

/**
 *
 * @author Tamires Freitas
 * */
public class Paciente extends Pessoa{
    private double altura, peso;
    private String alergia, objetivoEstetico;

    public Paciente(double altura, double peso, String alergia, String objetivoEstetico, String nome, 
            String cpf, String dataNascimento, String telefone, String email, Endereco endereco, SexoEnum sexo) {
        super(nome, cpf, dataNascimento, telefone, email, endereco, sexo);
        this.altura = altura;
        this.peso = peso;
        this.alergia = alergia;
        this.objetivoEstetico = objetivoEstetico;
    }

    public Paciente(double altura, double peso, String alergia, String objetivoEstetico, int id, String nome, 
            String cpf, String dataNascimento, String telefone, String email, Endereco endereco, SexoEnum sexo) {
        super(id, nome, cpf, dataNascimento, telefone, email, endereco, sexo);
        this.altura = altura;
        this.peso = peso;
        this.alergia = alergia;
        this.objetivoEstetico = objetivoEstetico;
    }
    
    
    @Override
    public String toString() {
        return super.toString() +
               "\nPeso: " + getPeso() +
               "\nAltura: " + getAltura() +
               "\nAlergias: " + getAlergia() +
               "\nObjetivo Estetico: " + getObjetivoEstetico()+
                "\n";
    }
    

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getAlergia() {
        return alergia;
    }

    public void setAlergia(String alergia) {
        this.alergia = alergia;
    }

    public String getObjetivoEstetico() {
        return objetivoEstetico;
    }

    public void setObjetivoEstetico(String objetivoEstetico) {
        this.objetivoEstetico = objetivoEstetico;
    }
    
}
