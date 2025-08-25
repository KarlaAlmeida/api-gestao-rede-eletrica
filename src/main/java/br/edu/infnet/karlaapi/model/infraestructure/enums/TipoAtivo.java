package br.edu.infnet.karlaapi.model.infraestructure.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TipoAtivo {
    POSTE, TRANSFORMADOR, CHAVE_FUSIVEL, PARA_RAIOS, REGULADOR;

    @JsonCreator
    public static TipoAtivo fromString(String value) {
        for (TipoAtivo tipoAtivo : TipoAtivo.values()) {
            if (tipoAtivo.name().equalsIgnoreCase(value)) {
                return tipoAtivo;
            }
        }
        throw new IllegalArgumentException(
                "Tipo de Ativo " + value + " inválido. " +
                        "O Status deve ser APOSTE, TRANSFORMADOR, CHAVE_FUSIVEL, PARA_RAIOS ou REGULADOR.");
    }
}
