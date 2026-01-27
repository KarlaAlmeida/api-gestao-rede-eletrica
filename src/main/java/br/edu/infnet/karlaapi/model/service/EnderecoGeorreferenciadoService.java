package br.edu.infnet.karlaapi.model.service;

import br.edu.infnet.karlaapi.model.clients.OpenStreetMapFeignClient;
import br.edu.infnet.karlaapi.model.clients.ViaCepFeignClient;
import br.edu.infnet.karlaapi.model.domain.dto.out.EnderecoGeorreferenciadoResponseDTO;
import br.edu.infnet.karlaapi.model.domain.entities.EnderecoGeorreferenciado;
import br.edu.infnet.karlaapi.model.domain.entities.Geolocalizacao;
import br.edu.infnet.karlaapi.model.infraestructure.exceptions.CepNotFoundException;
import br.edu.infnet.karlaapi.model.infraestructure.exceptions.ExternalApiException;
import br.edu.infnet.karlaapi.model.infraestructure.exceptions.InvalidCepException;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoGeorreferenciadoService {

    //private final GeolocalizacaoFeignClient geolocalizacaoFeignClient;
    private final ViaCepFeignClient viaCepFeignClient;
    private final OpenStreetMapFeignClient openStreetMapFeignClient;

    public EnderecoGeorreferenciadoService(ViaCepFeignClient viaCepFeignClient,
                           OpenStreetMapFeignClient openStreetMapFeignClient) {
        this.viaCepFeignClient = viaCepFeignClient;
        this.openStreetMapFeignClient = openStreetMapFeignClient;
    }

    public EnderecoGeorreferenciado obterEnderecoGeorreferenciadoPorCep(String cep){

        /*if (cep == null || !cep.matches("\\d{8}")) {
            throw new InvalidCepException("O CEP deve conter 8 dígitos.");
        }*/
        String cepLimpo = cep.replaceAll("\\D", "");

        if (!cepLimpo.matches("\\d{8}")) {
            throw new InvalidCepException("O CEP deve conter 8 dígitos.");
        }

        try {
            EnderecoGeorreferenciado endereco = viaCepFeignClient.findByCep(cepLimpo);

            if (endereco != null) {
                String query = endereco.getLogradouro() + ", " + endereco.getLocalidade() + ", " + endereco.getUf();
                List<Geolocalizacao> geolocalizacoes = openStreetMapFeignClient.search(query, "jsonv2", 10);

                if (geolocalizacoes != null && !geolocalizacoes.isEmpty()) {
                    Geolocalizacao geolocalizacao = geolocalizacoes.get(0);
                    endereco.setLatitude(geolocalizacao.getLat());
                    endereco.setLongitude(geolocalizacao.getLon());
                }
            }

            /*EnderecoGeorreferenciadoResponseDTO enderecoGeorreferenciadoResponseDTO =
                    new EnderecoGeorreferenciadoResponseDTO(endereco);
            return enderecoGeorreferenciadoResponseDTO;*/

            return endereco;

        }catch (FeignException.NotFound e) {
            throw new CepNotFoundException("CEP não encontrado: " + cep);
        } catch (FeignException e) {
            throw new ExternalApiException("Erro na comunicação com a API do viacep ou openStreetMap: " + e.getMessage());
        }
    }
}
