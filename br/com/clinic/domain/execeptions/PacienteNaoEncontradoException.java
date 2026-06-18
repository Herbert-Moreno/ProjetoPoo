package br.com.clinic.domain.execeptions;

public class PacienteNaoEncontradoException extends RuntimeException {
    public PacienteNaoEncontradoException(Long id) {
        super("Cliente não encontrado. Id: " + id);
    }
}
