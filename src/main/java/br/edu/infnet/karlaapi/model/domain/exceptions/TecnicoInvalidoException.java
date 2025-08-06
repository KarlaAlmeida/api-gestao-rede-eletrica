package br.edu.infnet.karlaapi.model.domain.exceptions;

public class TecnicoInvalidoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TecnicoInvalidoException(String mensagem) {
        super(mensagem);
    }
}
