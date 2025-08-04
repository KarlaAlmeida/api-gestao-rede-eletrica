package br.edu.infnet.karlaapi.model.domain.entities;

import br.edu.infnet.karlaapi.model.domain.enums.PrioridadeOcorrecia;
import br.edu.infnet.karlaapi.model.domain.enums.StatusOS;
import br.edu.infnet.karlaapi.model.domain.enums.StatusOcorrecia;

import java.time.LocalDate;
import java.util.UUID;

public class OrdemServico {

    private Integer id;
    private Ocorrencia ocorrencia;
    private Tecnico tecnico;
    private String descricaoServico;
    private LocalDate dataCriacaoOS;
    private LocalDate dataConclusaoOS;
    private StatusOS statusOS;

    @Override
    public String toString() {
        return String.format("Ordem de Serviço - ID: %d - %s - %s - Descrição Serviço %s - " +
                        "Data criação OS: %s - Data conclusão OS: %s - Status: %s",
                id, ocorrencia, tecnico, descricaoServico, dataCriacaoOS, dataConclusaoOS, statusOS);
    }
}
