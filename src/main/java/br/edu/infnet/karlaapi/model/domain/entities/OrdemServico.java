package br.edu.infnet.karlaapi.model.domain.entities;

import br.edu.infnet.karlaapi.model.infraestructure.enums.StatusOS;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ocorrencia_id")
    private Ocorrencia ocorrencia;

    @OneToOne
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnico;

    @Column(name = "descricao_servico", length = 100)
    private String descricaoServico;
    @Column(name = "data_criacao_OS", length = 100)
    private LocalDate dataCriacaoOS;
    @Column(name = "data_conclusao_OS", length = 100)
    private LocalDate dataConclusaoOS;
    @Enumerated(EnumType.STRING)
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
