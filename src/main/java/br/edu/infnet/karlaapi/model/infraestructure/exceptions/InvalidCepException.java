package br.edu.infnet.karlaapi.model.infraestructure.exceptions;

public class InvalidCepException extends RuntimeException {
    public InvalidCepException(String message) {
        super(message);
    }
}
