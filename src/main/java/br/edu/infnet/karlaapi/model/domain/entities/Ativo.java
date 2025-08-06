package br.edu.infnet.karlaapi.model.domain.entities;

import br.edu.infnet.karlaapi.model.domain.enums.StatusAtivo;
import br.edu.infnet.karlaapi.model.domain.enums.TipoAtivo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class Ativo {

    private Integer id;
    private TipoAtivo tipoAtivo;
    private Endereco endereco;
    private LocalDate dataInstalacao;
    private StatusAtivo statusAtivo;

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
