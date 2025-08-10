package br.edu.infnet.karlaapi.controller;

import br.edu.infnet.karlaapi.model.domain.entities.Ativo;
import br.edu.infnet.karlaapi.model.service.AtivoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ativos")
public class AtivoController {

    private final AtivoService ativoService;

    public AtivoController(AtivoService ativoService) {
        this.ativoService = ativoService;
    }

    @PostMapping
    public Ativo incluir(@RequestBody Ativo ativo) {
        return ativoService.incluir(ativo);
    }

    @PutMapping(value = "/{id}")
    public Ativo alterar(@PathVariable Integer id, @RequestBody Ativo ativo) {
        return ativoService.alterar(id, ativo);
    }

    //CRIAR UM PATCH

    @GetMapping(value = "/{id}")
    public Ativo obterPorId(@PathVariable Integer id){
        return ativoService.obterPorId(id);
    }

    @GetMapping
    public List<Ativo> obterLista(){
        return ativoService.obterLista();
    }

    @DeleteMapping(value = "/{id}")
    public void excluir(@PathVariable Integer id){
        ativoService.excluir(id);
    }
}
