package br.edu.infnet.karlaapi.model.domain.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Tecnico extends Funcionario {

    @NotBlank(message = "Especialidade é obrigatória")
    @Size(max = 100, message = "Especialidade deve ter no máximo 100 caracteres")
    @Column(name = "especialidade", length = 100)
    private String especialidade;

    @Column(name = "disponível")
    private boolean disponivel;

    @OneToMany(mappedBy = "tecnico", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<OrdemServico> ordensServico = new ArrayList<>();

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
