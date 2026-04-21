package br.edu.infnet.karlaapi.model.service;

import br.edu.infnet.karlaapi.model.clients.OpenStreetMapFeignClient;
import br.edu.infnet.karlaapi.model.clients.ViaCepFeignClient;
import br.edu.infnet.karlaapi.model.domain.entities.EnderecoGeorreferenciado;
import br.edu.infnet.karlaapi.model.domain.entities.Geolocalizacao;
import br.edu.infnet.karlaapi.model.infraestructure.exceptions.CepNotFoundException;
import br.edu.infnet.karlaapi.model.infraestructure.exceptions.ExternalApiException;
import br.edu.infnet.karlaapi.model.infraestructure.exceptions.InvalidCepException;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoGeorreferenciadoService {

    @Value("${api.openstreetmap.useragent}")
    private String userAgent;

    @Value("${api.openstreetmap.email}")
    private String email;

    //private final GeolocalizacaoFeignClient geolocalizacaoFeignClient;
    private final ViaCepFeignClient viaCepFeignClient;
    private final OpenStreetMapFeignClient openStreetMapFeignClient;

    public EnderecoGeorreferenciadoService(ViaCepFeignClient viaCepFeignClient,
                           OpenStreetMapFeignClient openStreetMapFeignClient) {
        this.viaCepFeignClient = viaCepFeignClient;
        this.openStreetMapFeignClient = openStreetMapFeignClient;
    }

    public EnderecoGeorreferenciado obterEnderecoGeorreferenciadoPorCep(String cep){


        String cepLimpo = cep.replaceAll("\\D", "");

        if (!cepLimpo.matches("\\d{8}")) {
            throw new InvalidCepException("O CEP deve conter 8 dígitos.");
        }

        try {
            EnderecoGeorreferenciado endereco = viaCepFeignClient.findByCep(cepLimpo);

            if (endereco != null) {
                String query = endereco.getLogradouro() + ", " + endereco.getLocalidade() + ", " + endereco.getUf();
                List<Geolocalizacao> geolocalizacoes = openStreetMapFeignClient.search(query, "jsonv2", 10, email, userAgent);

                if (geolocalizacoes != null && !geolocalizacoes.isEmpty()) {
                    Geolocalizacao geolocalizacao = geolocalizacoes.get(0);
                    endereco.setLatitude(geolocalizacao.getLat());
                    endereco.setLongitude(geolocalizacao.getLon());
                }
            }

            return endereco;

        }catch (FeignException.NotFound e) {
            throw new CepNotFoundException("CEP não encontrado: " + cep);
        } catch (FeignException e) {
            throw new ExternalApiException("Erro na comunicação com a API do viacep ou openStreetMap: " + e.getMessage());
        }
    }
}
