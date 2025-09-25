package br.edu.infnet.karlaapi.model.domain.dto.out;

import br.edu.infnet.karlaapi.model.domain.entities.Ocorrencia;
import br.edu.infnet.karlaapi.model.domain.entities.Tecnico;
import br.edu.infnet.karlaapi.model.infraestructure.enums.PrioridadeOcorrencia;
import br.edu.infnet.karlaapi.model.infraestructure.enums.StatusOS;
import br.edu.infnet.karlaapi.model.infraestructure.enums.StatusOcorrencia;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OcorrenciaResponseDTO {

    private Integer id;

    private AtivoResponseDTO ativo;

    private String descricaoOcorrencia;
    private PrioridadeOcorrencia prioridadeOcorrencia;
    private LocalDate dataRegistroOcorrencia;
    private StatusOcorrencia statusOcorrencia;

    private List<OrdemServicoResponseDTO> ordensServico;
    private List<Integer> idOS;
    private Tecnico tecnicoOS;
    private String descricaoServicoOS;
    private StatusOS statusOS;
    
    public OcorrenciaResponseDTO(Ocorrencia ocorrencia){
        this.setId(ocorrencia.getId());
        this.ativo = new AtivoResponseDTO(ocorrencia.getAtivo());
        this.setDescricaoOcorrencia(ocorrencia.getDescricaoOcorrencia());
        this.setPrioridadeOcorrencia(ocorrencia.getPrioridadeOcorrencia());
        this.setDataRegistroOcorrencia(ocorrencia.getDataRegistroOcorrencia());
        this.setStatusOcorrencia(ocorrencia.getStatusOcorrencia());
        this.ordensServico = ocorrencia.getOrdensServico()
                .stream()
                .map(OrdemServicoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return String.format("Ocorrência - ID: %d - %s - Descrição: %s - Data da Ocorrência %s" +
                        " Prioridade: %s - Status: %s",
                id, ativo, descricaoOcorrencia, dataRegistroOcorrencia,
                prioridadeOcorrencia, statusOcorrencia);
    }
}
