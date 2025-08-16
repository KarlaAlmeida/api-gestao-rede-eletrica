package br.edu.infnet.karlaapi.controller;

import br.edu.infnet.karlaapi.model.domain.dto.OrdemServicoRequestDTO;
import br.edu.infnet.karlaapi.model.domain.entities.OrdemServico;
import br.edu.infnet.karlaapi.model.infraestructure.enums.StatusOS;
import br.edu.infnet.karlaapi.model.service.OrdemServicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/ordem-servico")
public class OrdemServicoController {

    private final OrdemServicoService ordemServicoService;

    public OrdemServicoController(OrdemServicoService ordemServicoService) {
        this.ordemServicoService = ordemServicoService;
    }

    @PostMapping
    public ResponseEntity<OrdemServico> incluir(@RequestBody OrdemServicoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ordemServicoService.incluir(dto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<OrdemServico> alterar(@PathVariable Integer id, @RequestBody OrdemServicoRequestDTO dto) {
        if (dto == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ordemServicoService.alterar(id, dto));
    }

    @PatchMapping(value = "/{id}/status")
    public ResponseEntity<OrdemServico> alterarStatus(@PathVariable Integer id,
                                    @RequestParam StatusOS status){
        return ResponseEntity.ok(ordemServicoService.alterarStatus(id, status));
    }

    @PatchMapping(value = "/{id}/data-conclusao")
    public ResponseEntity<OrdemServico> alterarDataConclusao(@PathVariable Integer id,
                                                      @RequestParam LocalDate dataConclusao){
        return ResponseEntity.ok(ordemServicoService.alterarDataConclusao(id, dataConclusao));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrdemServico> obterPorId(@PathVariable Integer id){
        return ResponseEntity.ok(ordemServicoService.obterPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<OrdemServico>> obterLista(){

        List<OrdemServico> lista = ordemServicoService.obterLista();

        if(lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id){
        ordemServicoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
