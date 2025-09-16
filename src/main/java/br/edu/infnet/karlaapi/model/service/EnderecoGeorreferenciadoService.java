package br.edu.infnet.karlaapi.model.service;

import br.edu.infnet.karlaapi.model.clients.GeolocalizacaoFeignClient;
import br.edu.infnet.karlaapi.model.domain.entities.EnderecoGeorreferenciado;
import org.springframework.stereotype.Service;

@Service
public class EnderecoGeorreferenciadoService {

    private final GeolocalizacaoFeignClient geolocalizacaoFeignClient;

    public EnderecoGeorreferenciadoService(GeolocalizacaoFeignClient geolocalizacaoFeignClient) {
        this.geolocalizacaoFeignClient = geolocalizacaoFeignClient;
    }

    public EnderecoGeorreferenciado obterEnderecoGeorreferenciadoPorCep(String cep){
        return geolocalizacaoFeignClient.obterEnderecoGeorreferenciadoPorCep(cep);
    }
}
