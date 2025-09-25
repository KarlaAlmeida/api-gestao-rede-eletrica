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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OcorrenciaService {

    private final OcorrenciaRepository ocorrenciaRepository;
    private final AtivoRepository ativoRepository;

    public OcorrenciaService(OcorrenciaRepository ocorrenciaRepository, AtivoRepository ativoRepository) {
        this.ocorrenciaRepository = ocorrenciaRepository;
        this.ativoRepository = ativoRepository;
    }

     public OcorrenciaResponseDTO incluir(OcorrenciaRequestDTO dto) {

        AtivoResponseDTO ativoResponseDTO = obterAtivoPorId(dto.getAtivoId());

        OcorrenciaResponseDTO ocorrenciaResponseDTO = new OcorrenciaResponseDTO();
        ocorrenciaResponseDTO.setAtivo(ativoResponseDTO);
        ocorrenciaResponseDTO.setDescricaoOcorrencia(dto.getDescricaoOcorrencia());
        ocorrenciaResponseDTO.setDataRegistroOcorrencia(LocalDate.now());
        ocorrenciaResponseDTO.setPrioridadeOcorrencia(PrioridadeOcorrencia.fromString(dto.getPrioridadeOcorrencia()));
        ocorrenciaResponseDTO.setStatusOcorrencia(StatusOcorrencia.REGISTRADA);

        Ocorrencia ocorrencia = new Ocorrencia(ocorrenciaResponseDTO);

        return new OcorrenciaResponseDTO(ocorrenciaRepository.save(ocorrencia));
    }

    public OcorrenciaResponseDTO alterar(Integer id, OcorrenciaRequestDTO dto) {

        OcorrenciaResponseDTO ocorrenciaResponseDTO = obterPorId(id);
        ocorrenciaResponseDTO.setId(id);
        AtivoResponseDTO ativoResponseDTO = obterAtivoPorId(dto.getAtivoId());
        ativoResponseDTO.setId(id);

        ocorrenciaResponseDTO.setAtivo(ativoResponseDTO);
        ocorrenciaResponseDTO.setDescricaoOcorrencia(dto.getDescricaoOcorrencia());
        PrioridadeOcorrencia prioridadeOcorrencia =
                PrioridadeOcorrencia.fromString(dto.getPrioridadeOcorrencia());
        ocorrenciaResponseDTO.setPrioridadeOcorrencia(prioridadeOcorrencia);

        Ocorrencia ocorrencia = new Ocorrencia(ocorrenciaResponseDTO);

        return new OcorrenciaResponseDTO(ocorrenciaRepository.save(ocorrencia));
    }

    public OcorrenciaResponseDTO alterarStatus(Integer id, String statusNovo){
        OcorrenciaResponseDTO ocorrenciaResponseDTO = obterPorId(id);

        StatusOcorrencia statusOcorrenciaNovo = StatusOcorrencia.fromString(statusNovo);

        if(statusOcorrenciaNovo.equals(ocorrenciaResponseDTO.getStatusOcorrencia())){
            throw new IllegalStateException("O status atual da ocorrência já é " + statusOcorrenciaNovo);
        }

        ocorrenciaResponseDTO.setStatusOcorrencia(statusOcorrenciaNovo);

        Ocorrencia ocorrencia = new Ocorrencia(ocorrenciaResponseDTO);

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

    public void excluir(Integer id) {
        OcorrenciaResponseDTO ocorrenciaResponseDTO = obterPorId(id);
        ocorrenciaRepository.delete(new Ocorrencia(ocorrenciaResponseDTO));
    }

}
