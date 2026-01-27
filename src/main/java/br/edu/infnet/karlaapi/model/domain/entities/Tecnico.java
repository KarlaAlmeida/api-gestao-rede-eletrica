package br.edu.infnet.karlaapi.model.domain.entities;

import br.edu.infnet.karlaapi.model.domain.dto.out.TecnicoResponseDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
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

    /*public Tecnico(TecnicoResponseDTO tecnicoResponseDTO){
        this.setId(tecnicoResponseDTO.getId());
        this.setNome(tecnicoResponseDTO.getNome());
        this.setCpf(tecnicoResponseDTO.getCpf());
        this.setEmail(tecnicoResponseDTO.getEmail());
        this.setTelefone(tecnicoResponseDTO.getTelefone());
        this.setEndereco(new EnderecoGeorreferenciado(tecnicoResponseDTO.getEndereco()));
        this.setUltimoSalario(tecnicoResponseDTO.getUltimoSalario());
        this.setAtivo(tecnicoResponseDTO.isAtivo());
        this.setEspecialidade(tecnicoResponseDTO.getEspecialidade());
        this.setDisponivel(tecnicoResponseDTO.isDisponivel());
        this.ordensServico = Optional.ofNullable(tecnicoResponseDTO.getOrdensServico())
                .orElseGet(ArrayList::new)
                .stream()
                .map(OrdemServico::new)
                .collect(Collectors.toList());
    }*/


    @Override
    public String toString() {
        return String.format("%s - Especialidade: %s - Disponível: %s",
                super.toString(), especialidade, disponivel ? "Sim" : "Não");
    }
}
