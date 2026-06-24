package br.com.clinic.model.classes.pessoa;

/**
 *
 * @author Tamires Freitas
 * */
import br.com.clinic.model.enums.SexoEnum;

public class Profissional extends Pessoa {
    private String cargo, especialidade, registroProfissional, dataContratacao;
    private double salario;

    public Profissional(String cargo, String especialidade, String registroProfissional, String dataContratacao,
                        double salario, String nome, String cpf, String dataNascimento, String telefone, String email, Endereco endereco, SexoEnum sexo) {
        super(nome, cpf, dataNascimento, telefone, email, endereco, sexo);
        this.cargo = cargo;
        this.especialidade = especialidade;
        this.registroProfissional = registroProfissional;
        this.dataContratacao = dataContratacao;
        this.salario = salario;
    }

    public Profissional(String cargo, String especialidade, String registroProfissional, String dataContratacao, 
            double salario, int id, String nome, String cpf, String dataNascimento, String telefone, String email, Endereco endereco, SexoEnum sexo) {
        super(id, nome, cpf, dataNascimento, telefone, email, endereco, sexo);
        this.cargo = cargo;
        this.especialidade = especialidade;
        this.registroProfissional = registroProfissional;
        this.dataContratacao = dataContratacao;
        this.salario = salario;
    }
    
    @Override
    public String toString(){
        return super.toString()+
                "\nCargo: "+ getCargo()+
                "\nEspecialidade: "+ getEspecialidade()+
                "\nRegistro profissional: "+ getRegistroProfissional()+
                "\nData contratacao: "+ getDataContratacao()+
                "\nSalario: "+ getSalario()+
                "\n";
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getRegistroProfissional() {
        return registroProfissional;
    }

    public void setRegistroProfissional(String registroProfissional) {
        this.registroProfissional = registroProfissional;
    }

    public String getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(String dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
    
}