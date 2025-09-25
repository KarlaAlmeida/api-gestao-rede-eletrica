package br.edu.infnet.karlaapi.model.domain.dto.out;

import br.edu.infnet.karlaapi.model.domain.entities.Tecnico;
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
public class TecnicoResponseDTO{

    private Integer id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private EnderecoResponseDTO endereco;
    private double ultimoSalario;
    private boolean ativo;
    private String especialidade;
    private boolean disponivel;
    private List<OrdemServicoResponseDTO> ordensServico = new ArrayList<>();

    public TecnicoResponseDTO(Tecnico tecnico){
        this.setId(tecnico.getId());
        this.setNome(tecnico.getNome());
        this.setCpf(tecnico.getCpf());
        this.setEmail(tecnico.getEmail());
        this.setTelefone(tecnico.getTelefone());
        this.endereco = new EnderecoResponseDTO(tecnico.getEndereco());
        this.setUltimoSalario(tecnico.getUltimoSalario());
        this.setAtivo(tecnico.isAtivo());
        this.setEspecialidade(tecnico.getEspecialidade());
        this.setDisponivel(tecnico.isDisponivel());
        this.ordensServico = Optional.ofNullable(tecnico.getOrdensServico())
                .orElseGet(ArrayList::new)
                .stream()
                .map(OrdemServicoResponseDTO::new)
                .collect(Collectors.toList());
    }

}
