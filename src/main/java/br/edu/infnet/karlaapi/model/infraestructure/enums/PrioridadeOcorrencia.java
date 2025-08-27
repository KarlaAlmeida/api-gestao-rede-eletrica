package br.edu.infnet.karlaapi.model.infraestructure.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PrioridadeOcorrencia {
    BAIXA, MEDIA, ALTA, CRITICA;

    @JsonCreator
    public static PrioridadeOcorrencia fromString(String value) {
        for (PrioridadeOcorrencia prioridade : PrioridadeOcorrencia.values()) {
            if (prioridade.name().equalsIgnoreCase(value)) {
                return prioridade;
            }
        }
        throw new IllegalArgumentException(
                "Prioridade " + value + " inválida. A prioridade deve ser BAIXA, MEDIA, ALTA ou CRITICA.");
    }
}
