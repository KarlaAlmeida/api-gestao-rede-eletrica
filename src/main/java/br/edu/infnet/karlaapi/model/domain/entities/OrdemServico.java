package br.edu.infnet.karlaapi.model.domain.entities;

import br.edu.infnet.karlaapi.model.domain.enums.StatusOS;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OrdemServico {

    private Integer id;
    private Ocorrencia ocorrencia;
    private Tecnico tecnico;
    private String descricaoServico;
    private LocalDate dataCriacaoOS;
    private LocalDate dataConclusaoOS;
    private StatusOS statusOS;

    public OrdemServico(Integer id, Ocorrencia ocorrencia, Tecnico tecnico,
                        String descricaoServico, LocalDate dataCriacaoOS,
                        LocalDate dataConclusaoOS, StatusOS statusOS) {
        this.id = id;
        this.ocorrencia = ocorrencia;
        this.tecnico = tecnico;
        this.descricaoServico = descricaoServico;
        this.dataCriacaoOS = dataCriacaoOS;
        this.dataConclusaoOS = dataConclusaoOS;
        this.statusOS = statusOS;
    }

    public OrdemServico() {
    }

    @Override
    public String toString() {
        return String.format("Ordem de Serviço - ID: %d - %s - %s - Descrição Serviço %s - " +
                        "Data criação OS: %s - Data conclusão OS: %s - Status: %s",
                id, ocorrencia, tecnico, descricaoServico, dataCriacaoOS, dataConclusaoOS, statusOS);
    }
}
