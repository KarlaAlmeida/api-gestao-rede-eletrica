package br.edu.infnet.karlaapi.model.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlterarDisponibilidadeDTO {

    @NotNull
    private Boolean disponivel;
}
