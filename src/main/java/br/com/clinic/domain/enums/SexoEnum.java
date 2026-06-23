package br.com.clinic.domain.enums;

/**
 *
 * @author Tamires Freitas
 * */
public enum SexoEnum {
    F,
    M,
    I;
    
    @Override
    public String toString() {
        if (this == F) {
            return "Feminino";
        } else if (this == I) {
            return "Intersexo";
        }
        return "Masculino";
    }
}
