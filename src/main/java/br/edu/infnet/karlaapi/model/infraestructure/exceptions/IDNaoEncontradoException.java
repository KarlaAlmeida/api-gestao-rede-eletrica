package br.edu.infnet.karlaapi.model.infraestructure.exceptions;

public class IDNaoEncontradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public IDNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
