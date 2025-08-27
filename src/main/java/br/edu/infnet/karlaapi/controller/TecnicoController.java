package br.edu.infnet.karlaapi.controller;

import br.edu.infnet.karlaapi.model.domain.entities.Tecnico;
import br.edu.infnet.karlaapi.model.service.TecnicoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Tecnico> incluir(@Valid @RequestBody Tecnico tecnico) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tecnicoService.incluir(tecnico));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Tecnico> alterar(@PathVariable Integer id,
                                           @Valid @RequestBody Tecnico tecnico) {

        if (tecnico == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tecnicoService.alterar(id, tecnico));
    }

    @PatchMapping(value = "/{id}/inativar")
    public ResponseEntity<Tecnico> inativar(@PathVariable Integer id) {
        return ResponseEntity.ok(tecnicoService.inativar(id));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Tecnico> obterPorId(@PathVariable Integer id){
        return ResponseEntity.ok(tecnicoService.obterPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Tecnico>> obterLista(){
        List<Tecnico> lista = tecnicoService.obterLista();

        if(lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("filtro/nome-e-especialidade")
    public List<Tecnico> buscarPorNomeEspecialidade(@RequestParam String nomePrefixo,
                                                    @RequestParam String especialidade) {
        return tecnicoService.buscarPorNomeEspecialidade(nomePrefixo, especialidade);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id){
        tecnicoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
