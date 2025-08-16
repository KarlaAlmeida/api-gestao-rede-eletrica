package br.edu.infnet.karlaapi.model.service;

import br.edu.infnet.karlaapi.model.domain.dto.OcorrenciaRequestDTO;
import br.edu.infnet.karlaapi.model.domain.entities.Ativo;
import br.edu.infnet.karlaapi.model.domain.entities.Ocorrencia;
import br.edu.infnet.karlaapi.model.infraestructure.enums.StatusOcorrecia;
import br.edu.infnet.karlaapi.model.infraestructure.exceptions.AtributoInvalidoException;
import br.edu.infnet.karlaapi.model.infraestructure.exceptions.IDNaoEncontradoException;
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

    private void validar(OcorrenciaRequestDTO dto) {
        if(dto == null) {
            throw new IllegalArgumentException("A ocorrência não pode estar nula!");
        }

        if(dto.getAtivoId() == null ||
                dto.getDescricaoOcorrencia() == null ||
                dto.getPrioridadeOcorrecia() == null) {
            throw new AtributoInvalidoException("Todas as informações devem ser preenchidas!");
        }
    }

    public Ocorrencia incluir(OcorrenciaRequestDTO dto) {
        validar(dto);

        Ativo ativo = ativoRepository.findById(dto.getAtivoId())
                .orElseThrow(() -> new RuntimeException("Ativo não encontrado"));

        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setAtivo(ativo);
        ocorrencia.setDescricaoOcorrencia(dto.getDescricaoOcorrencia());
        ocorrencia.setDataRegistroOcorrencia(LocalDate.now());
        ocorrencia.setPrioridadeOcorrecia(dto.getPrioridadeOcorrecia());
        ocorrencia.setStatusOcorrecia(StatusOcorrecia.REGISTRADA);

        return ocorrenciaRepository.save(ocorrencia);
    }

    public Ocorrencia alterar(Integer id, OcorrenciaRequestDTO dto) {

        validar(dto);

        Ocorrencia ocorrencia = ocorrenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada"));

        ocorrencia.setDescricaoOcorrencia(dto.getDescricaoOcorrencia());
        ocorrencia.setPrioridadeOcorrecia(dto.getPrioridadeOcorrecia());

        return ocorrenciaRepository.save(ocorrencia);
    }

    public Ocorrencia alterarStatus(Integer id, StatusOcorrecia status){
        Ocorrencia ocorrencia = obterPorId(id);

        if(ocorrencia == null) {
            throw new IllegalArgumentException("Não foi possível obter a ocorrência pelo ID " + id);
        }

        if(status.equals(ocorrencia.getStatusOcorrecia())){
            throw new IllegalStateException("O status atual da ocorrência já é " + status);
        }

        ocorrencia.setStatusOcorrecia(status);
        return ocorrenciaRepository.save(ocorrencia);
    }


    public Ocorrencia obterPorId(Integer id) {
       return ocorrenciaRepository.findById(id).orElseThrow(()->
                new IDNaoEncontradoException("A ocorrência com ID " + id + " não foi encontrado."));
    }

    public List<Ocorrencia> obterLista() {
        return ocorrenciaRepository.findAll();
    }

    public void excluir(Integer id) {
        Ocorrencia ocorrencia = ocorrenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("A ocorrência com ID" + id + " não foi encontrado."));

        ocorrenciaRepository.delete(ocorrencia);
    }

}
