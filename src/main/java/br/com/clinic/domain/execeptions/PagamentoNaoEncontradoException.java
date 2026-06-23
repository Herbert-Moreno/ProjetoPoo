package br.com.clinic.domain.execeptions;

public class PagamentoNaoEncontradoException extends RuntimeException {
    public PagamentoNaoEncontradoException(Long id) {
        super("Lançamento nao encontrado com o ID: " + id);
    }
}
