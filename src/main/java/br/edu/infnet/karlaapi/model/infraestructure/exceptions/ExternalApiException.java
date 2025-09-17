package br.edu.infnet.karlaapi.model.infraestructure.exceptions;

public class ExternalApiException extends RuntimeException {
    public ExternalApiException(String message) {
        super(message);
    }
}
