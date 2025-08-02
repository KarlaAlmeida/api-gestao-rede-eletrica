package br.edu.infnet.karlaapi.model.domain.entities;

import br.edu.infnet.karlaapi.model.domain.enums.PrioridadeOcorrecia;
import br.edu.infnet.karlaapi.model.domain.enums.StatusOcorrecia;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Ocorrencia {

    private Integer id;
    private Ativo ativo;
    private String descricaoOcorrencia;
    private LocalDate dataRegistroOcorrencia;
    private PrioridadeOcorrecia prioridadeOcorrecia;
    private StatusOcorrecia statusOcorrecia;

    @Override
    public String toString() {
        return String.format("Ocorrência: %d - %s - %s - %s - %s - %s",
                id, ativo, descricaoOcorrencia, dataRegistroOcorrencia, prioridadeOcorrecia, statusOcorrecia);
    }
}
