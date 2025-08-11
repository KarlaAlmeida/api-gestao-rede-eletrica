package br.edu.infnet.karlaapi.controller;

import br.edu.infnet.karlaapi.model.domain.entities.OrdemServico;
import br.edu.infnet.karlaapi.model.domain.enums.StatusOS;
import br.edu.infnet.karlaapi.model.service.OrdemServicoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ordem-servico")
public class OrdemServicoController {

    private final OrdemServicoService ordemServicoService;

    public OrdemServicoController(OrdemServicoService ordemServicoService) {
        this.ordemServicoService = ordemServicoService;
    }

    @PostMapping
    public OrdemServico incluir(@RequestBody OrdemServico ordemServico) {
        return ordemServicoService.incluir(ordemServico);
    }

    @PutMapping(value = "/{id}")
    public OrdemServico alterar(@PathVariable Integer id, @RequestBody OrdemServico ordemServico) {
        return ordemServicoService.alterar(id, ordemServico);
    }

    @PatchMapping(value = "/{id}/status")
    public OrdemServico alterarStatus(@PathVariable Integer id,
                                    @RequestParam StatusOS status){
        return ordemServicoService.alterarStatus(id, status);
    }

    @GetMapping(value = "/{id}")
    public OrdemServico obterPorId(@PathVariable Integer id){
        return ordemServicoService.obterPorId(id);
    }

    @GetMapping
    public List<OrdemServico> obterLista(){
        return ordemServicoService.obterLista();
    }

    @DeleteMapping(value = "/{id}")
    public void excluir(@PathVariable Integer id){
        ordemServicoService.excluir(id);
    }
}
