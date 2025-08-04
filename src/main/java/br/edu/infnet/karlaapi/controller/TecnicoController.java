package br.edu.infnet.karlaapi.controller;

import br.edu.infnet.karlaapi.model.domain.entities.Tecnico;
import br.edu.infnet.karlaapi.model.service.TecnicoService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/tecnicos")
public class TecnicoController {

    private final TecnicoService tecnicoService;

    public TecnicoController(TecnicoService tecnicoService) {
        this.tecnicoService = tecnicoService;
    }

    @GetMapping
    public Tecnico obterTecnico(){
        return tecnicoService.obter();
    }

    @GetMapping("/lista")
    public List<Tecnico> obterListaTecnicos(){
        return tecnicoService.obterLista();
    }

    @DeleteMapping
    public void excluirTecnico(Integer id){
        tecnicoService.excluir(id);
    }
}
