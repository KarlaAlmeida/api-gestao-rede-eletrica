package br.edu.infnet.karlaapi.model.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP deve estar no formato 00000-000")
    @Column(name = "cep", length = 9)
    private String cep;

    @NotBlank(message = "Rua é obrigatória")
    @Size(max = 100, message = "Rua deve ter no máximo 100 caracteres")
    @Column(name = "rua", length = 100)
    private String rua;

    @NotNull(message = "Número é obrigatório")
    @Positive(message = "Número deve ser maior que zero")
    @Column(name = "numero", length = 5)
    private Integer numero;

    @Size(max = 100, message = "Complemento deve ter no máximo 100 caracteres")
    @Column(name = "complemento", length = 100)
    private String complemento;

    @NotBlank(message = "Cidade é obrigatória")
    @Size(max = 100, message = "Cidade deve ter no máximo 100 caracteres")
    @Column(name = "cidade", length = 100)
    private String cidade;

    @NotBlank(message = "Estado é obrigatório")
    @Size(max = 100, message = "Estado deve ter no máximo 100 caracteres")
    @Column(name = "estado", length = 100)
    private String estado;

    @Override
    public String toString() {
        return String.format("id %d, rua %s, numero %d, complemento %s, cidade %s," +
                        " estado %s, CEP: %s",
                id, rua, numero, complemento, cidade, estado, cep);
    }
}
