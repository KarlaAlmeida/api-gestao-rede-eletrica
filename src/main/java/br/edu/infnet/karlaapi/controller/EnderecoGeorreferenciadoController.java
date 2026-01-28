package br.edu.infnet.karlaapi.controller;

import br.edu.infnet.karlaapi.model.domain.entities.EnderecoGeorreferenciado;
import br.edu.infnet.karlaapi.model.service.EnderecoGeorreferenciadoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enderecos")
public class EnderecoGeorreferenciadoController {

    private final EnderecoGeorreferenciadoService enderecoGeorreferenciadoService;


    public EnderecoGeorreferenciadoController(EnderecoGeorreferenciadoService enderecoGeorreferenciadoService) {
        this.enderecoGeorreferenciadoService = enderecoGeorreferenciadoService;
    }

    @GetMapping("/{cep}")
    public ResponseEntity<EnderecoGeorreferenciado> obterLocalidade(@PathVariable String cep) {

        EnderecoGeorreferenciado enderecoGeorreferenciado =
                enderecoGeorreferenciadoService.obterEnderecoGeorreferenciadoPorCep(cep);

        return ResponseEntity.ok(enderecoGeorreferenciado);
    }
}
