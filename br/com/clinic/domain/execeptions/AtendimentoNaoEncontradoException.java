package br.com.clinic.domain.execeptions;

public class AtendimentoNaoEncontradoException extends RuntimeException {
    public AtendimentoNaoEncontradoException(Long id) {
        super("Atendimento não encontrado, ID: " + id);
    }
}
