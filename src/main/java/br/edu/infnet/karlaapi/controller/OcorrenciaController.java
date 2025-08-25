package br.edu.infnet.karlaapi.controller;

import br.edu.infnet.karlaapi.model.domain.dto.in.OcorrenciaRequestDTO;
import br.edu.infnet.karlaapi.model.domain.entities.Ocorrencia;
import br.edu.infnet.karlaapi.model.service.OcorreciaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Ocorrencia> incluir(@Valid @RequestBody OcorrenciaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ocorreciaService.incluir(dto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Ocorrencia> alterar(@PathVariable Integer id,
                                              @Valid @RequestBody OcorrenciaRequestDTO dto) {

        if (dto == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ocorreciaService.alterar(id, dto));

    }

    @PatchMapping(value = "/{id}/status")
    public ResponseEntity<Ocorrencia> alterarStatus(@PathVariable Integer id,
                                                    @RequestParam String statusOcorrencia){
        return ResponseEntity.ok(ocorreciaService.alterarStatus(id, statusOcorrencia));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Ocorrencia> obterPorId(@PathVariable Integer id){

        return ResponseEntity.ok(ocorreciaService.obterPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Ocorrencia>> obterLista(){

        List<Ocorrencia> lista = ocorreciaService.obterLista();

        if(lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id){
        ocorreciaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
