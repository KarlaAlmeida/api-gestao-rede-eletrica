package br.edu.infnet.karlaapi.model.domain.dto.out;

import br.edu.infnet.karlaapi.model.domain.entities.EnderecoGeorreferenciado;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoResponseDTO {

    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private int numero;
    private String complementoNumero;

    public EnderecoResponseDTO(EnderecoGeorreferenciado enderecoGeorreferenciado) {
        this.setCep(enderecoGeorreferenciado.getCep());
        this.setLogradouro(enderecoGeorreferenciado.getLogradouro());
        this.setComplemento(enderecoGeorreferenciado.getComplemento());
        this.setBairro(enderecoGeorreferenciado.getBairro());
        this.setLocalidade(enderecoGeorreferenciado.getLocalidade());
        this.setUf(enderecoGeorreferenciado.getUf());
        this.setNumero(enderecoGeorreferenciado.getNumero());
        this.setComplementoNumero(enderecoGeorreferenciado.getComplementoNumero());
    }
}
