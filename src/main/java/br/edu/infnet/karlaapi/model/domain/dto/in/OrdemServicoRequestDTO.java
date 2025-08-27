package br.edu.infnet.karlaapi.model.domain.dto.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrdemServicoRequestDTO {


    @NotNull(message = "O ID da ocorrência é obrigatório.")
    private Integer ocorrenciaId;

    @NotNull(message = "O ID do técnico é obrigatório.")
    private Integer tecnicoId;

    @NotBlank(message = "A descrição do serviço é obrigatória.")
    private String descricaoServico;

}
