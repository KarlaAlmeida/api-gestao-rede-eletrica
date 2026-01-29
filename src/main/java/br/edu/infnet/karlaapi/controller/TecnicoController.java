package br.edu.infnet.karlaapi.controller;

import br.edu.infnet.karlaapi.model.domain.dto.AlterarDisponibilidadeDTO;
import br.edu.infnet.karlaapi.model.domain.dto.in.TecnicoRequestDTO;
import br.edu.infnet.karlaapi.model.domain.dto.out.TecnicoResponseDTO;
import br.edu.infnet.karlaapi.model.service.TecnicoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/tecnicos")
public class TecnicoController {

    private final TecnicoService tecnicoService;

    public TecnicoController(TecnicoService tecnicoService) {
        this.tecnicoService = tecnicoService;
    }

    @PostMapping
    public ResponseEntity<TecnicoResponseDTO> incluir(@Valid @RequestBody TecnicoRequestDTO tecnico) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tecnicoService.incluir(tecnico));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TecnicoResponseDTO> alterar(@PathVariable Integer id,
                                           @Valid @RequestBody TecnicoRequestDTO tecnico) {

        if (tecnico == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tecnicoService.alterar(id, tecnico));
    }

    @PatchMapping(value = "/{id}/inativar")
    public ResponseEntity<TecnicoResponseDTO> inativar(@PathVariable Integer id) {
        return ResponseEntity.ok(tecnicoService.inativar(id));
    }

    @PatchMapping(
            value = "/{id}/status",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<TecnicoResponseDTO> alterarStatus(
            @PathVariable Integer id,
            @RequestBody Map<String, Boolean> payload
    ) {
        Boolean ativo = payload.get("ativo");

        if (ativo == null) {
            return ResponseEntity.badRequest().build();
        }

        TecnicoResponseDTO dto = tecnicoService.alterarStatus(id, ativo);

        return ResponseEntity.ok(dto);
    }

    @PatchMapping(
            value = "/{id}/disponibilidade",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<TecnicoResponseDTO> alterarDisponibilidade(
            @PathVariable Integer id,
            @RequestBody @Valid AlterarDisponibilidadeDTO dto
    ) {
        TecnicoResponseDTO response =
                tecnicoService.alterarDisponibilidade(id, dto.getDisponivel());

        return ResponseEntity.ok(response);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<TecnicoResponseDTO> obterPorId(@PathVariable Integer id){
        return ResponseEntity.ok(tecnicoService.obterPorId(id));
    }


    @GetMapping
    public ResponseEntity<Page<TecnicoResponseDTO>> obterLista(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {

        Page<TecnicoResponseDTO> pagina = tecnicoService.obterLista(page, size);

        if (pagina.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(pagina);
    }

    @GetMapping("filtro/nome-e-especialidade")
    public List<TecnicoResponseDTO> buscarPorNomeEspecialidade(@RequestParam String nomePrefixo,
                                                    @RequestParam String especialidade) {
        return tecnicoService.buscarPorNomeEspecialidade(nomePrefixo, especialidade);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id){
        tecnicoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
