package br.edu.infnet.karlaapi.model.domain.entities;

import br.edu.infnet.karlaapi.model.infraestructure.enums.PrioridadeOcorrecia;
import br.edu.infnet.karlaapi.model.infraestructure.enums.StatusOcorrecia;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ativo_id")
    private Ativo ativo;

    @Column(name = "descricao_ocorrencia", length = 100)
    private String descricaoOcorrencia;
    @Column(name = "data_ocorrencia", length = 100)
    private LocalDate dataRegistroOcorrencia;
    @Enumerated(EnumType.STRING)
    private PrioridadeOcorrecia prioridadeOcorrecia;
    @Enumerated(EnumType.STRING)
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
