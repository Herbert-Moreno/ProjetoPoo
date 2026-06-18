package br.com.clinic.domain.execeptions;

public class ProcedimentoInvalidoException extends RuntimeException {
    public ProcedimentoInvalidoException(String message) {
        super(message);
    }
}
