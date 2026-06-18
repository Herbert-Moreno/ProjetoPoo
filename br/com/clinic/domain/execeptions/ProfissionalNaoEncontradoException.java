package br.com.clinic.domain.execeptions;

public class ProfissionalNaoEncontradoException extends RuntimeException {
    public ProfissionalNaoEncontradoException(Long id) {
        super("Profisional não encontrado. Id: " + id);
    }
}
