package br.edu.infnet.karlaapi.model.domain.dto;

import br.edu.infnet.karlaapi.model.infraestructure.enums.StatusOcorrencia;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlterarStatusOcorrenciaDTO {
    @NotNull
    private StatusOcorrencia statusOcorrencia;
}
