package br.edu.infnet.karlaapi.controller;

import br.edu.infnet.karlaapi.model.domain.entities.Tecnico;
import br.edu.infnet.karlaapi.model.service.TecnicoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tecnicos")
public class TecnicoController {

    private final TecnicoService tecnicoService;

    public TecnicoController(TecnicoService tecnicoService) {
        this.tecnicoService = tecnicoService;
    }

    @PostMapping
    public Tecnico incluir(@RequestBody Tecnico tecnico) {
        return tecnicoService.incluir(tecnico);
    }

    @PutMapping(value = "/{id}")
    public Tecnico alterar(@PathVariable Integer id, @RequestBody Tecnico tecnico) {
        return tecnicoService.alterar(id, tecnico);
    }

    @PatchMapping(value = "/{id}/inativar")
    public Tecnico inativar(@PathVariable Integer id) {
        return tecnicoService.inativar(id);
    }

    @GetMapping(value = "/{id}")
    public Tecnico obterPorId(@PathVariable Integer id){
        return tecnicoService.obterPorId(id);
    }

    @GetMapping
    public List<Tecnico> obterLista(){
        return tecnicoService.obterLista();
    }

    @DeleteMapping(value = "/{id}")
    public void excluir(@PathVariable Integer id){
        tecnicoService.excluir(id);
    }
}
