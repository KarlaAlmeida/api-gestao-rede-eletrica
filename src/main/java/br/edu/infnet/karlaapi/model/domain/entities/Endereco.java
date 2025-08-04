package br.edu.infnet.karlaapi.model.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {

    private String cep;
    private String rua;
    private Long numero;
    private String complemento;
    private String cidade;
    private String estado;

    @Override
    public String toString() {
        return String.format("Endereço: rua %s, numero %d, complemento %s, cidade %s," +
                        " estado %s, CEP: %s",
                rua, numero, complemento, cidade, estado, cep);
    }
}
