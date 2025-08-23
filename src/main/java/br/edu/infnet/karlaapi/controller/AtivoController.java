package br.edu.infnet.karlaapi.controller;

import br.edu.infnet.karlaapi.model.domain.entities.Ativo;
import br.edu.infnet.karlaapi.model.infraestructure.enums.StatusAtivo;
import br.edu.infnet.karlaapi.model.service.AtivoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Ativo> incluir(@RequestBody Ativo ativo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ativoService.incluir(ativo));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Ativo> alterar(@PathVariable Integer id, @RequestBody Ativo ativo) {

        if (ativo == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ativoService.alterar(id, ativo));

    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Ativo> alterarStatus(@PathVariable Integer id, @RequestParam StatusAtivo status){
        return ResponseEntity.ok(ativoService.alterarStatus(id, status));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Ativo> obterPorId(@PathVariable Integer id){
        return ResponseEntity.ok(ativoService.obterPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Ativo>> obterLista(){

        List<Ativo> lista = ativoService.obterLista();

        if(lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id){
        ativoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
