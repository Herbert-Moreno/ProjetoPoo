package br.com.clinic.model.classes.pessoa;

/**
 * @author Tamires Freitas
 * */
public class Endereco {
    private String rua, bairro, cidade, cep;

    public Endereco(String rua, String bairro, String cidade, String cep) {
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.cep = cep;
    }
    
    @Override
    public String toString() {
        return "Rua: " + getRua() +
               ", Bairro: " + getBairro() +
               ", Cidade: " + getCidade() +
               ", CEP: " + getCep();
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
    
}
