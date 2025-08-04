package br.edu.infnet.karlaapi.model.domain.entities;

import br.edu.infnet.karlaapi.model.domain.enums.StatusAtivo;
import br.edu.infnet.karlaapi.model.domain.enums.TipoAtivo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Ativo {

    private Integer id;
    private TipoAtivo tipoAtivo;
    private String identificador;
    private Endereco endereco;
    private LocalDate dataInstalacao;
    private StatusAtivo statusAtivo;

    @Override
    public String toString() {
        return String.format("Ativo - ID: %d - Tipo: %s - Identificador: %s - Endereço: %s - " +
                        "Data de instalação: %s - Status: %s",
                id, tipoAtivo, identificador, endereco, dataInstalacao, statusAtivo);
    }
}
