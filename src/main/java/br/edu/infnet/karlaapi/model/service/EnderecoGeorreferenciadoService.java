package br.edu.infnet.karlaapi.model.service;

import br.edu.infnet.karlaapi.model.clients.GeolocalizacaoFeignClient;
import br.edu.infnet.karlaapi.model.domain.entities.EnderecoGeorreferenciado;
import br.edu.infnet.karlaapi.model.infraestructure.exceptions.CepNotFoundException;
import br.edu.infnet.karlaapi.model.infraestructure.exceptions.ExternalApiException;
import br.edu.infnet.karlaapi.model.infraestructure.exceptions.InvalidCepException;
import feign.FeignException;
import org.springframework.stereotype.Service;

@Service
public class EnderecoGeorreferenciadoService {

    private final GeolocalizacaoFeignClient geolocalizacaoFeignClient;

    public EnderecoGeorreferenciadoService(GeolocalizacaoFeignClient geolocalizacaoFeignClient) {
        this.geolocalizacaoFeignClient = geolocalizacaoFeignClient;
    }

    public EnderecoGeorreferenciado obterEnderecoGeorreferenciadoPorCep(String cep){

        if (cep == null || !cep.matches("\\d{8}")) {
            throw new InvalidCepException("O CEP deve conter 8 dígitos.");
        }

        try {
            return geolocalizacaoFeignClient.obterEnderecoGeorreferenciadoPorCep(cep);
        } catch (FeignException.NotFound e) {
            throw new CepNotFoundException("CEP não encontrado: " + cep);
        } catch (FeignException e) {
            throw new ExternalApiException("Erro na comunicação com a API de geolocalização: " + e.getMessage());
        }
    }
}
