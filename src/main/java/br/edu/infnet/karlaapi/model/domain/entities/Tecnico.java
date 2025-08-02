package br.edu.infnet.karlaapi.model.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Tecnico extends Funcionario {

    private Integer id;
    private String especialidade;
    private boolean disponivel;

    @Override
    public String toString() {
        return String.format("%s - Id: %d - Especialidade: %s - Disponível: %s",
                super.toString(), id, especialidade, disponivel ? "Sim" : "Não");
    }
}
