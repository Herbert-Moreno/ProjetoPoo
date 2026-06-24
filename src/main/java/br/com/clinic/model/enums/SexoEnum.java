package br.com.clinic.model.enums;

/**
 *
 * @author Tamires Freitas
 * */
public enum SexoEnum {
    F,
    M;
    
    @Override
    public String toString() {
        if (this == F) {
            return "Feminino";
        }
        return "Masculino";
    }
}
