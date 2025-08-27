package br.edu.infnet.karlaapi.model.domain.entities;

import br.edu.infnet.karlaapi.model.infraestructure.enums.PrioridadeOcorrencia;
import br.edu.infnet.karlaapi.model.infraestructure.enums.StatusOcorrencia;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "descricao_ocorrencia")
    private String descricaoOcorrencia;

    @Enumerated(EnumType.STRING)
    private PrioridadeOcorrencia prioridadeOcorrencia;

    @Column(name = "data_ocorrencia")
    private LocalDate dataRegistroOcorrencia;

    @Enumerated(EnumType.STRING)
    private StatusOcorrencia statusOcorrencia;

    @OneToMany(mappedBy = "ocorrencia", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<OrdemServico> ordensServico = new ArrayList<>();

    public Ocorrencia(Integer id, Ativo ativo, String descricaoOcorrencia,
                      LocalDate dataRegistroOcorrencia, PrioridadeOcorrencia prioridadeOcorrencia,
                      StatusOcorrencia statusOcorrencia) {
        this.id = id;
        this.ativo = ativo;
        this.descricaoOcorrencia = descricaoOcorrencia;
        this.dataRegistroOcorrencia = dataRegistroOcorrencia;
        this.prioridadeOcorrencia = prioridadeOcorrencia;
        this.statusOcorrencia = statusOcorrencia;
    }

    public Ocorrencia() {
    }

    @Override
    public String toString() {
        return String.format("Ocorrência - ID: %d - %s - Descrição: %s - Data da Ocorrência %s" +
                        " Prioridade: %s - Status: %s",
                id, ativo, descricaoOcorrencia, dataRegistroOcorrencia,
                prioridadeOcorrencia, statusOcorrencia);
    }
}
