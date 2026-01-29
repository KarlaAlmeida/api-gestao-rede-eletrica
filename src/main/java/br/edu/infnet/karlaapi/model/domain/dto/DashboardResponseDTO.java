package br.edu.infnet.karlaapi.model.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponseDTO {


    private long totalAtivos;
    private long totalTecnicos;


    private long totalOcorrencias;
    private long ocorrenciasConcluidas;


    private long totalOrdensServico;
    private long ordensServicoConcluidas;


    private Map<String, Long> ocorrenciasPorTipoAtivo;
    private Map<String, Long> ordensServicoPorTecnico;
}
