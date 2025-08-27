package br.edu.infnet.karlaapi.model.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    @Column(name = "nome", length = 100)
    private String nome;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos")
    @Column(name = "cpf", length = 11)
    private String cpf;

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    @Column(name = "email", length = 100)
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}",
            message = "Telefone inválido. Use o formato (XX) XXXXX-XXXX ou (XX) XXXX-XXXX.")
    @Column(name = "telefone", length = 11)
    private String telefone;

    @NotNull(message = "Endereço é obrigatório")
    @Valid
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @PositiveOrZero(message = "Salário deve ser maior ou igual a zero")
    @Column(name = "ultimo_salario")
    private double ultimoSalario;

    @Column(name = "ativo")
    private boolean ativo;

    @Override
    public String toString() {
        return String.format("Funcionario %s - ID: %d - CPF: %s - E-mail: %s - Telefone: %s " +
                        "Endereço: %s - Último Salário: R$%.2f - Situação: %s",
                nome, id, cpf, email, telefone, endereco, ultimoSalario,
                ativo ? "Ativo" : "Inativo ");
    }
}
