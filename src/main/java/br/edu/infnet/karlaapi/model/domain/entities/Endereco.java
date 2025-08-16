package br.edu.infnet.karlaapi.model.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cep", length = 9)
    private String cep;
    @Column(name = "rua", length = 100)
    private String rua;
    @Column(name = "numero", length = 5)
    private Integer numero;
    @Column(name = "complemento", length = 100)
    private String complemento;
    @Column(name = "cidade", length = 100)
    private String cidade;
    @Column(name = "estado", length = 100)
    private String estado;

    @Override
    public String toString() {
        return String.format("id %d, rua %s, numero %d, complemento %s, cidade %s," +
                        " estado %s, CEP: %s",
                id, rua, numero, complemento, cidade, estado, cep);
    }
}
