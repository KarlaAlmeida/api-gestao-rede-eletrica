package br.edu.infnet.karlaapi.controller;

import br.edu.infnet.karlaapi.model.domain.dto.in.AtivoRequestDTO;
import br.edu.infnet.karlaapi.model.domain.dto.out.AtivoResponseDTO;
import br.edu.infnet.karlaapi.model.service.AtivoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/ativos")
@CrossOrigin(origins = "*")
public class AtivoController {

    private final AtivoService ativoService;

    public AtivoController(AtivoService ativoService) {
        this.ativoService = ativoService;
    }

    @PostMapping
    public ResponseEntity<AtivoResponseDTO> incluir(@Valid @RequestBody AtivoRequestDTO ativoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ativoService.incluir(ativoDTO));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AtivoResponseDTO> alterar(@PathVariable Integer id,
                                         @Valid @RequestBody AtivoRequestDTO ativoDTO) {

        if (ativoDTO == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ativoService.alterar(id, ativoDTO));

    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<AtivoResponseDTO> alterarStatus(@PathVariable Integer id,
                                               @RequestParam String status){
        return ResponseEntity.ok(ativoService.alterarStatus(id, status));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AtivoResponseDTO> obterPorId(@PathVariable Integer id){
        return ResponseEntity.ok(ativoService.obterPorId(id));
    }

    /*@GetMapping
    public ResponseEntity<List<AtivoResponseDTO>> obterLista(){

        List<AtivoResponseDTO> lista = ativoService.obterLista();

        if(lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }*/

    @GetMapping
    public ResponseEntity<Page<AtivoResponseDTO>> obterLista(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {

        Page<AtivoResponseDTO> pagina = ativoService.obterLista(page, size);

        if (pagina.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(pagina);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id){
        ativoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
