package br.edu.infnet.karlaapi.controller;

import br.edu.infnet.karlaapi.model.domain.entities.Ativo;
import br.edu.infnet.karlaapi.model.domain.entities.Ocorrencia;
import br.edu.infnet.karlaapi.model.domain.enums.PrioridadeOcorrecia;
import br.edu.infnet.karlaapi.model.domain.enums.StatusAtivo;
import br.edu.infnet.karlaapi.model.domain.enums.StatusOcorrecia;
import br.edu.infnet.karlaapi.model.service.AtivoService;
import br.edu.infnet.karlaapi.model.service.OcorreciaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ocorrencias")
public class OcorrenciaController {

    private final OcorreciaService ocorreciaService;

    public OcorrenciaController(OcorreciaService ocorreciaService) {
        this.ocorreciaService = ocorreciaService;
    }

    @PostMapping
    public Ocorrencia incluir(@RequestBody Ocorrencia ocorrencia) {
        return ocorreciaService.incluir(ocorrencia);
    }

    @PutMapping(value = "/{id}")
    public Ocorrencia alterar(@PathVariable Integer id, @RequestBody Ocorrencia ocorrencia) {
        return ocorreciaService.alterar(id, ocorrencia);
    }

    @PatchMapping(value = "/{id}/status")
    public Ocorrencia alterarStatus(@PathVariable Integer id,
                                    @RequestParam StatusOcorrecia status){
        return ocorreciaService.alterarStatus(id, status);
    }

    @PatchMapping(value = "/{id}/prioridade")
    public Ocorrencia alterarPrioridade(@PathVariable Integer id,
                                        @RequestParam PrioridadeOcorrecia prioridade){
        return ocorreciaService.alterarPrioridade(id, prioridade);
    }

    @GetMapping(value = "/{id}")
    public Ocorrencia obterPorId(@PathVariable Integer id){
        return ocorreciaService.obterPorId(id);
    }

    @GetMapping
    public List<Ocorrencia> obterLista(){
        return ocorreciaService.obterLista();
    }

    @DeleteMapping(value = "/{id}")
    public void excluir(@PathVariable Integer id){
        ocorreciaService.excluir(id);
    }
}
