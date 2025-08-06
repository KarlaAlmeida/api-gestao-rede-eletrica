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

    @GetMapping(value = "/{id}")
    public Tecnico obterTecnicoPorId(@PathVariable Integer id){
        return tecnicoService.obterporId(id);
    }

    @GetMapping
    public List<Tecnico> obterListaTecnicos(){
        return tecnicoService.obterLista();
    }

    @DeleteMapping
    public void excluirTecnico(Integer id){
        tecnicoService.excluir(id);
    }
}
