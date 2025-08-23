package br.edu.infnet.karlaapi.model.domain.dto;

import br.edu.infnet.karlaapi.model.infraestructure.enums.PrioridadeOcorrecia;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OcorrenciaRequestDTO {

    private Integer ativoId;
    private String descricaoOcorrencia;
    private PrioridadeOcorrecia prioridadeOcorrecia;

}
