package br.edu.infnet.karlaapi.model.domain.dto;


import br.edu.infnet.karlaapi.model.infraestructure.enums.PrioridadeOcorrencia;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlterarPrioridadeOcorrenciaDTO {
    @NotNull
    private PrioridadeOcorrencia prioridadeOcorrencia;
}
