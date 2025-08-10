package br.edu.infnet.karlaapi.model.domain.exceptions;

public class IDNaoEncontradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public IDNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
