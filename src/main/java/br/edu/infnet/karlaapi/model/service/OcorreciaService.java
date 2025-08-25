package br.edu.infnet.karlaapi.model.service;

import br.edu.infnet.karlaapi.model.domain.dto.in.OcorrenciaRequestDTO;
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
public class OcorreciaService{

    private final OcorrenciaRepository ocorrenciaRepository;
    private final AtivoRepository ativoRepository;

    public OcorreciaService(OcorrenciaRepository ocorrenciaRepository, AtivoRepository ativoRepository) {
        this.ocorrenciaRepository = ocorrenciaRepository;
        this.ativoRepository = ativoRepository;
    }

     public Ocorrencia incluir(OcorrenciaRequestDTO dto) {

        Ativo ativo = obterAtivoPorId(dto.getAtivoId());

        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setAtivo(ativo);
        ocorrencia.setDescricaoOcorrencia(dto.getDescricaoOcorrencia());
        ocorrencia.setDataRegistroOcorrencia(LocalDate.now());
        ocorrencia.setPrioridadeOcorrencia(PrioridadeOcorrencia.fromString(dto.getPrioridadeOcorrencia()));
        ocorrencia.setStatusOcorrencia(StatusOcorrencia.REGISTRADA);

        return ocorrenciaRepository.save(ocorrencia);
    }

    public Ocorrencia alterar(Integer id, OcorrenciaRequestDTO dto) {

        Ocorrencia ocorrencia = obterPorId(id);
        Ativo ativo = obterAtivoPorId(dto.getAtivoId());

        ocorrencia.setAtivo(ativo);

        ocorrencia.setDescricaoOcorrencia(dto.getDescricaoOcorrencia());

        PrioridadeOcorrencia prioridadeOcorrencia = PrioridadeOcorrencia.fromString(dto.getPrioridadeOcorrencia());
        ocorrencia.setPrioridadeOcorrencia(prioridadeOcorrencia);

        return ocorrenciaRepository.save(ocorrencia);
    }

    public Ocorrencia alterarStatus(Integer id, String statusNovo){
        Ocorrencia ocorrencia = obterPorId(id);

        StatusOcorrencia statusOcorrenciaNovo = StatusOcorrencia.fromString(statusNovo);

        if(statusOcorrenciaNovo.equals(ocorrencia.getStatusOcorrencia())){
            throw new IllegalStateException("O status atual da ocorrência já é " + statusOcorrenciaNovo);
        }

        ocorrencia.setStatusOcorrencia(statusOcorrenciaNovo);
        return ocorrenciaRepository.save(ocorrencia);
    }

    public Ocorrencia obterPorId(Integer id) {
       return ocorrenciaRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("A ocorrência com ID " + id + " não foi encontrada."));
    }

    public Ativo obterAtivoPorId(Integer id) {
        return ativoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Ativo não encontrado pelo ID " + id));
    }

    public List<Ocorrencia> obterLista() {
        return ocorrenciaRepository.findAll();
    }

    public void excluir(Integer id) {
        Ocorrencia ocorrencia = obterPorId(id);
        ocorrenciaRepository.delete(ocorrencia);
    }

}
