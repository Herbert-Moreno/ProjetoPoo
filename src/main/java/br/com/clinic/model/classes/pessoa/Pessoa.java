package br.com.clinic.model.classes.pessoa;

import br.com.clinic.model.enums.SexoEnum;

/**
 *
 * @author Tamires Freitas
 * */
public abstract class Pessoa {
    private String nome, cpf, dataNascimento, telefone, email;
    private int id;
    private Endereco endereco;
    private SexoEnum sexo;

    public Pessoa(String nome, String cpf, String dataNascimento, String telefone, 
            String email, Endereco endereco, SexoEnum sexo) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.sexo = sexo;
    }

    public Pessoa(int id, String nome, String cpf, String dataNascimento, 
            String telefone, String email, Endereco endereco, SexoEnum sexo) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.sexo = sexo;
    }
    
    @Override
    public String toString() {
        return "Nome: " + getNome() +
               "\nCPF: " + getCpf() +
               "\nSexo: "+ getSexo()+
               "\nData nascimento: "+getDataNascimento()+
               "\nTelefone: " + getTelefone() +
               "\nEmail: " + getEmail() +
               "\nEndereco: " +getEndereco();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SexoEnum getSexo() {
        return sexo;
    }

    public void setSexo(SexoEnum sexo) {
        this.sexo = sexo;
    }
    
    
    
}
