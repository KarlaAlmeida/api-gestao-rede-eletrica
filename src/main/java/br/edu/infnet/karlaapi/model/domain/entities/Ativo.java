package br.edu.infnet.karlaapi.model.domain.entities;

import br.edu.infnet.karlaapi.model.infraestructure.enums.StatusAtivo;
import br.edu.infnet.karlaapi.model.infraestructure.enums.TipoAtivo;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
public class Ativo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Tipo de ativo é obrigatório")
    @Enumerated(EnumType.STRING)
    private TipoAtivo tipoAtivo;

    @NotNull(message = "Status do ativo é obrigatório")
    @Enumerated(EnumType.STRING)
    private StatusAtivo statusAtivo;

    @NotNull(message = "Data de instalação é obrigatória")
    @PastOrPresent(message = "Data de instalação deve estar no passado ou hoje.")
    @Column(name = "data_instalacao", length = 100)
    private LocalDate dataInstalacao;

    @NotNull(message = "Endereço é obrigatório")
    @Valid
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    public Ativo(Integer id, TipoAtivo tipoAtivo, Endereco endereco, LocalDate dataInstalacao,
                 StatusAtivo statusAtivo) {
        this.id = id;
        this.tipoAtivo = tipoAtivo;
        this.endereco = endereco;
        this.dataInstalacao = dataInstalacao;
        this.statusAtivo = statusAtivo;
    }

    public Ativo(){
    }

    @Override
    public String toString() {
        return String.format("Ativo - ID: %d - Tipo: %s - Endereço: %s - " +
                        "Data de instalação: %s - Status: %s",
                id, tipoAtivo, endereco, dataInstalacao, statusAtivo);
    }
}
