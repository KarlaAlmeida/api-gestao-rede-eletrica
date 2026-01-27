package br.edu.infnet.karlaapi.model.service;

import br.edu.infnet.karlaapi.model.domain.dto.in.OcorrenciaRequestDTO;
import br.edu.infnet.karlaapi.model.domain.dto.out.AtivoResponseDTO;
import br.edu.infnet.karlaapi.model.domain.dto.out.OcorrenciaResponseDTO;
import br.edu.infnet.karlaapi.model.domain.entities.Ativo;
import br.edu.infnet.karlaapi.model.domain.entities.Ocorrencia;
import br.edu.infnet.karlaapi.model.infraestructure.enums.PrioridadeOcorrencia;
import br.edu.infnet.karlaapi.model.infraestructure.enums.StatusOcorrencia;
import br.edu.infnet.karlaapi.model.infraestructure.exceptions.ResourceNotFoundException;
import br.edu.infnet.karlaapi.model.repository.AtivoRepository;
import br.edu.infnet.karlaapi.model.repository.OcorrenciaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class OcorrenciaService {

    private final OcorrenciaRepository ocorrenciaRepository;
    private final AtivoRepository ativoRepository;

    public OcorrenciaService(OcorrenciaRepository ocorrenciaRepository, AtivoRepository ativoRepository) {
        this.ocorrenciaRepository = ocorrenciaRepository;
        this.ativoRepository = ativoRepository;
    }

    public OcorrenciaResponseDTO incluir(OcorrenciaRequestDTO dto) {

        Ativo ativo = ativoRepository.findById(dto.getAtivoId())
                .orElseThrow(() -> new ResourceNotFoundException("Ativo não encontrado"));

        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setAtivo(ativo);
        ocorrencia.setDescricaoOcorrencia(dto.getDescricaoOcorrencia());
        ocorrencia.setPrioridadeOcorrencia(
                PrioridadeOcorrencia.fromString(dto.getPrioridadeOcorrencia())
        );
        ocorrencia.setDataRegistroOcorrencia(LocalDate.now());
        ocorrencia.setStatusOcorrencia(StatusOcorrencia.REGISTRADA);

        ocorrencia = ocorrenciaRepository.save(ocorrencia);

        return new OcorrenciaResponseDTO(ocorrencia);
    }


    public OcorrenciaResponseDTO alterar(Integer id, OcorrenciaRequestDTO dto) {

        Ocorrencia ocorrencia = ocorrenciaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("A ocorrência com ID " + id + " não foi encontrada.")
                );

        if (dto.getAtivoId() != null) {
            Ativo ativo = ativoRepository.findById(dto.getAtivoId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Ativo não encontrado pelo ID " + dto.getAtivoId())
                    );
            ocorrencia.setAtivo(ativo);
        }

        if (dto.getDescricaoOcorrencia() != null) {
            ocorrencia.setDescricaoOcorrencia(dto.getDescricaoOcorrencia());
        }

        if (dto.getPrioridadeOcorrencia() != null) {
            ocorrencia.setPrioridadeOcorrencia(
                    PrioridadeOcorrencia.fromString(dto.getPrioridadeOcorrencia())
            );
        }

        Ocorrencia ocorrenciaAtualizada = ocorrenciaRepository.save(ocorrencia);

        return new OcorrenciaResponseDTO(ocorrenciaAtualizada);
    }


    public OcorrenciaResponseDTO alterarStatus(Integer id, StatusOcorrencia statusNovo) {

        Ocorrencia ocorrencia = ocorrenciaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("A ocorrência com ID " + id + " não foi encontrada.")
                );

        if (statusNovo.equals(ocorrencia.getStatusOcorrencia())) {
            throw new IllegalStateException(
                    "O status atual da ocorrência já é " + statusNovo
            );
        }

        ocorrencia.setStatusOcorrencia(statusNovo);

        Ocorrencia ocorrenciaAtualizada = ocorrenciaRepository.save(ocorrencia);

        return new OcorrenciaResponseDTO(ocorrenciaAtualizada);
    }



    public OcorrenciaResponseDTO alterarPrioridade(Integer id, PrioridadeOcorrencia prioridade) {
        Ocorrencia ocorrencia = ocorrenciaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Ocorrência não encontrada"));

        if (prioridade.equals(ocorrencia.getPrioridadeOcorrencia())) {
            throw new IllegalStateException("A prioridade já é " + prioridade);
        }

        ocorrencia.setPrioridadeOcorrencia(prioridade);
        return new OcorrenciaResponseDTO(ocorrenciaRepository.save(ocorrencia));
    }

    public OcorrenciaResponseDTO obterPorId(Integer id) {
        Ocorrencia ocorrencia = ocorrenciaRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("A ocorrência com ID " + id + " não foi encontrada."));
        return new OcorrenciaResponseDTO(ocorrencia);
    }

    public AtivoResponseDTO obterAtivoPorId(Integer id) {
        Ativo ativo = ativoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Ativo não encontrado pelo ID " + id));

        return new AtivoResponseDTO(ativo);
    }

    public List<OcorrenciaResponseDTO> obterLista() {
        return ocorrenciaRepository.findAll()
                .stream()
                .map(OcorrenciaResponseDTO::new) // chama o construtor DTO(Tecnico)
                .toList();
    }

    public Page<OcorrenciaResponseDTO> obterLista(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        return ocorrenciaRepository.findAll(pageable)
                .map(OcorrenciaResponseDTO::new);
    }

    public void excluir(Integer id) {
        Ocorrencia ocorrencia = ocorrenciaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("A ocorrência com ID " + id + " não foi encontrada.")
                );

        ocorrenciaRepository.delete(ocorrencia);
    }


}
