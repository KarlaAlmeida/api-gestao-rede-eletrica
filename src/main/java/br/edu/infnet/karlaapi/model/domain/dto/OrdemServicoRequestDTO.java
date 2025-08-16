package br.edu.infnet.karlaapi.model.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrdemServicoRequestDTO {

    private Integer ocorrenciaId;
    private Integer tecnicoId;
    private String descricaoServico;

}
