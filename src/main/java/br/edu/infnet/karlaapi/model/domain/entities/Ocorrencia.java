package br.edu.infnet.karlaapi.model.domain.entities;

import br.edu.infnet.karlaapi.model.domain.enums.PrioridadeOcorrecia;
import br.edu.infnet.karlaapi.model.domain.enums.StatusOcorrecia;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class Ocorrencia {

    private Integer id;
    private Ativo ativo;
    private String descricaoOcorrencia;
    private LocalDate dataRegistroOcorrencia;
    private PrioridadeOcorrecia prioridadeOcorrecia;
    private StatusOcorrecia statusOcorrecia;

    public Ocorrencia(Integer id, Ativo ativo, String descricaoOcorrencia,
                      LocalDate dataRegistroOcorrencia, PrioridadeOcorrecia prioridadeOcorrecia,
                      StatusOcorrecia statusOcorrecia) {
        this.id = id;
        this.ativo = ativo;
        this.descricaoOcorrencia = descricaoOcorrencia;
        this.dataRegistroOcorrencia = dataRegistroOcorrencia;
        this.prioridadeOcorrecia = prioridadeOcorrecia;
        this.statusOcorrecia = statusOcorrecia;
    }

    public Ocorrencia() {
    }

    @Override
    public String toString() {
        return String.format("Ocorrência - ID: %d - %s - Descrição: %s - Data da Ocorrência %s" +
                        " Prioridade: %s - Status: %s",
                id, ativo, descricaoOcorrencia, dataRegistroOcorrencia,
                prioridadeOcorrecia, statusOcorrecia);
    }
}
