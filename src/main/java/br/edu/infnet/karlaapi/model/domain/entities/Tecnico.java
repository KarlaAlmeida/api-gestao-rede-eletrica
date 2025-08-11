package br.edu.infnet.karlaapi.model.domain.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Tecnico extends Funcionario {

    private String especialidade;
    private boolean disponivel;

    public Tecnico(Integer id, String nome, String cpf, String email, String telefone,
                   Endereco endereco, double ultimoSalario, boolean ehAtivo,
                   String especialidade, boolean disponivel) {
        super(id, nome, cpf, email, telefone, endereco, ultimoSalario, ehAtivo);
        this.especialidade = especialidade;
        this.disponivel = disponivel;
    }

    public Tecnico() {
    }

    @Override
    public String toString() {
        return String.format("%s - Especialidade: %s - Disponível: %s",
                super.toString(), especialidade, disponivel ? "Sim" : "Não");
    }
}
