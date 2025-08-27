package br.edu.infnet.karlaapi.model.domain.dto.in;

import br.edu.infnet.karlaapi.model.domain.entities.Endereco;
import br.edu.infnet.karlaapi.model.infraestructure.enums.StatusAtivo;
import br.edu.infnet.karlaapi.model.infraestructure.enums.TipoAtivo;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AtivoRequestDTO {


    @NotNull(message = "Tipo de ativo é obrigatório")
    private String tipoAtivo;

    @NotNull(message = "Data de instalação é obrigatória")
    @PastOrPresent(message = "Data de instalação deve estar no passado ou hoje.")
    private LocalDate dataInstalacao;

    @NotNull(message = "Endereço é obrigatório")
    @Valid
    private Endereco endereco;

}
