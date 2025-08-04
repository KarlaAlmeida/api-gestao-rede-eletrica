package br.edu.infnet.karlaapi.model.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Funcionario {

    private Integer id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private double ultimoSalario;
    private boolean ehAtivo;

    @Override
    public String toString() {
        return String.format("Funcionario %s - Matricula: %d - CPF: %s - E-mail: %s - Telefone: %s " +
                        "- Último Salário: R$%.2f - Situação: %s",
                nome, id, cpf, email, telefone, ultimoSalario, ehAtivo ? "Ativo" : "Inativo ");
    }
}
