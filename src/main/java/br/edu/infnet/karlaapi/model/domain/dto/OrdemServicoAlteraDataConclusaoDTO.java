package br.edu.infnet.karlaapi.model.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdemServicoAlteraDataConclusaoDTO {

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataConclusaoOS;

    public LocalDate getDataConclusaoOS() {
        return dataConclusaoOS;
    }

    public void setDataConclusaoOS(LocalDate dataConclusaoOS) {
        this.dataConclusaoOS = dataConclusaoOS;
    }
}
