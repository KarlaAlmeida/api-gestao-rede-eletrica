package br.edu.infnet.karlaapi.model.infraestructure.exceptions;

public class CepNotFoundException extends RuntimeException {
    public CepNotFoundException(String message) {
        super(message);
    }
}
