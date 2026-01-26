package br.edu.infnet.karlaapi.controller;

import br.edu.infnet.karlaapi.model.domain.dto.in.OrdemServicoAlteraDataConclusaoDTO;
import br.edu.infnet.karlaapi.model.domain.dto.in.OrdemServicoRequestDTO;
import br.edu.infnet.karlaapi.model.domain.dto.out.OcorrenciaResponseDTO;
import br.edu.infnet.karlaapi.model.domain.dto.out.OrdemServicoResponseDTO;
import br.edu.infnet.karlaapi.model.service.OrdemServicoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/ordem-servico")
@CrossOrigin(origins = "*")
public class OrdemServicoController {

    private final OrdemServicoService ordemServicoService;

    public OrdemServicoController(OrdemServicoService ordemServicoService) {
        this.ordemServicoService = ordemServicoService;
    }

    @PostMapping
    public ResponseEntity<OrdemServicoResponseDTO> incluir(@Valid @RequestBody OrdemServicoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ordemServicoService.incluir(dto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<OrdemServicoResponseDTO> alterar(@PathVariable Integer id,
                                                @Valid @RequestBody OrdemServicoRequestDTO dto) {
        if (dto == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ordemServicoService.alterar(id, dto));
    }

    @PatchMapping(value = "/{id}/status")
    public ResponseEntity<OrdemServicoResponseDTO> alterarStatus(@PathVariable Integer id,
                                                      @RequestParam String statusOS){
        return ResponseEntity.ok(ordemServicoService.alterarStatus(id, statusOS));
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<OrdemServicoResponseDTO> alterarDataConclusao(@PathVariable Integer id,
                                                             @Valid @RequestBody
                                                             OrdemServicoAlteraDataConclusaoDTO dataConclusao){
        return ResponseEntity.ok(ordemServicoService.alterarDataConclusao(id, dataConclusao));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrdemServicoResponseDTO> obterPorId(@PathVariable Integer id){
        return ResponseEntity.ok(ordemServicoService.obterPorId(id));
    }

    /*@GetMapping
    public ResponseEntity<List<OrdemServicoResponseDTO>> obterLista(){

        List<OrdemServicoResponseDTO> lista = ordemServicoService.obterLista();

        if(lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }*/

    @GetMapping
    public ResponseEntity<Page<OrdemServicoResponseDTO>> obterLista(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {

        Page<OrdemServicoResponseDTO> pagina = ordemServicoService.obterLista(page, size);

        if (pagina.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/tecnico/{cpf}")
    public List<OrdemServicoResponseDTO> listarPorTecnico(@PathVariable String cpf) {
        return ordemServicoService.listarPorTecnico(cpf);
    }

    @GetMapping("filtro/descricao-e-periodo")
    public List<OrdemServicoResponseDTO> filtrarPorDescricaoServicoEPeriodo(
            @RequestParam String descricaoBusca,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        return ordemServicoService.filtrarPorDescricaoServicoEPeriodo(descricaoBusca, dataInicio, dataFim);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id){
        ordemServicoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
