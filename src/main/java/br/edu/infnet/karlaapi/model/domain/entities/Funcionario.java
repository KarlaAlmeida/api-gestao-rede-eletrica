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

    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private int matricula;
    private double ultimoSalario;
    private boolean ehAtivo;

    @Override
    public String toString() {
        return String.format("Funcionario %s - CPF: %s - E-mail: %s - Telefone: %s -" +
                        " Matricula: %d - Último Salário: R$%.2f - Situação: %s",
                nome, cpf, email, telefone, matricula,
                ultimoSalario, ehAtivo ? "Ativo" : "Inativo ");
    }
}
