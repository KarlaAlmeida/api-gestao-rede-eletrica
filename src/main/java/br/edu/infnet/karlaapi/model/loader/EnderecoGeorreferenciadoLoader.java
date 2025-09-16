package br.edu.infnet.karlaapi.model.loader;

import br.edu.infnet.karlaapi.model.domain.entities.EnderecoGeorreferenciado;
import br.edu.infnet.karlaapi.model.service.EnderecoGeorreferenciadoService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class EnderecoGeorreferenciadoLoader implements ApplicationRunner {

    private final EnderecoGeorreferenciadoService enderecoGeorreferenciadoService;

    public EnderecoGeorreferenciadoLoader(EnderecoGeorreferenciadoService enderecoGeorreferenciadoService) {
        this.enderecoGeorreferenciadoService = enderecoGeorreferenciadoService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        EnderecoGeorreferenciado enderecoGeorreferenciado =
                enderecoGeorreferenciadoService.obterEnderecoGeorreferenciadoPorCep("59150015");

        System.out.println("[RESULTADO] MEU CEP: " + enderecoGeorreferenciado.getCep());
        System.out.println("[RESULTADO] MEU LOGRADOURO: " + enderecoGeorreferenciado.getLogradouro());
        System.out.println("[RESULTADO] MEU COMPLEMENTO: " + enderecoGeorreferenciado.getComplemento());
        System.out.println("[RESULTADO] MEU BAIRRO: " + enderecoGeorreferenciado.getBairro());
        System.out.println("[RESULTADO] MEU MUNICÍPIO: " + enderecoGeorreferenciado.getLocalidade());
        System.out.println("[RESULTADO] MEU UF: " + enderecoGeorreferenciado.getUf());
        System.out.println("[RESULTADO] MINHA LATITUDE: " + enderecoGeorreferenciado.getLatitude());
        System.out.println("[RESULTADO] MINHA LONGITUDE: " + enderecoGeorreferenciado.getLongitude());
    }
}
