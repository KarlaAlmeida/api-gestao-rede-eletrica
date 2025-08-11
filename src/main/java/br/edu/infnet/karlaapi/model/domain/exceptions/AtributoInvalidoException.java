package br.edu.infnet.karlaapi.model.domain.exceptions;

public class AtributoInvalidoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AtributoInvalidoException(String mensagem) {
        super(mensagem);
    }
}
