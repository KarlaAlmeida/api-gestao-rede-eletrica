package br.edu.infnet.karlaapi.model.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Tecnico extends Funcionario {

    @Column(name = "especialidade", length = 100)
    private String especialidade;
    @Column(name = "disponível")
    private boolean disponivel;

    public Tecnico(Integer id, String nome, String cpf, String email, String telefone,
                   Endereco endereco, double ultimoSalario, boolean ativo,
                   String especialidade, boolean disponivel) {
        super(id, nome, cpf, email, telefone, endereco, ultimoSalario, ativo);
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
