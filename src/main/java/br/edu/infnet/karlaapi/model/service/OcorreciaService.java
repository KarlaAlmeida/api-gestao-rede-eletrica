package br.edu.infnet.karlaapi.model.service;

import br.edu.infnet.karlaapi.model.domain.entities.Ocorrencia;
import br.edu.infnet.karlaapi.model.infraestructure.enums.PrioridadeOcorrecia;
import br.edu.infnet.karlaapi.model.infraestructure.enums.StatusOcorrecia;
import br.edu.infnet.karlaapi.model.infraestructure.exceptions.AtributoInvalidoException;
import br.edu.infnet.karlaapi.model.infraestructure.exceptions.IDNaoEncontradoException;
import br.edu.infnet.karlaapi.model.repository.OcorrenciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OcorreciaService implements CrudService<Ocorrencia, Integer>{

    private final OcorrenciaRepository ocorrenciaRepository;

    public OcorreciaService(OcorrenciaRepository ocorrenciaRepository) {
        this.ocorrenciaRepository = ocorrenciaRepository;
    }

    private void validar(Ocorrencia ocorrencia) {
        if(ocorrencia == null) {
            throw new IllegalArgumentException("A ocorrência não pode estar nula!");
        }

        if(ocorrencia.getAtivo() == null ||
                ocorrencia.getDescricaoOcorrencia() == null ||
                ocorrencia.getDataRegistroOcorrencia() == null ||
                ocorrencia.getPrioridadeOcorrecia() == null ||
                ocorrencia.getStatusOcorrecia() == null) {
            throw new AtributoInvalidoException("Todas as informações devem ser preenchidas!");
        }
    }

    @Override
    public Ocorrencia incluir(Ocorrencia ocorrencia) {
        validar(ocorrencia);

        if(ocorrencia.getId() != null && ocorrencia.getId() != 0) {
            throw new IllegalArgumentException("Uma nova ocorrência não pode ter um ID na inclusão!");
        }

        return ocorrenciaRepository.save(ocorrencia);
    }

    @Override
    public Ocorrencia alterar(Integer id, Ocorrencia ocorrencia) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "O ID para alteração não pode ser nulo e deve ser maio que zero.");
        }

        validar(ocorrencia);
        obterPorId(id);
        ocorrencia.setId(id);
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

    public Ocorrencia alterarPrioridade(Integer id, PrioridadeOcorrecia prioridade){
        Ocorrencia ocorrencia = obterPorId(id);

        if(ocorrencia == null) {
            throw new IllegalArgumentException("Não foi possível obter a ocorrência pelo ID " + id);
        }

        if(prioridade.equals(ocorrencia.getPrioridadeOcorrecia())){
            throw new IllegalStateException("A prioridade atual da ocorrência já é " + prioridade);
        }

        ocorrencia.setPrioridadeOcorrecia(prioridade);
        return ocorrenciaRepository.save(ocorrencia);
    }

    @Override
    public Ocorrencia obterPorId(Integer id) {

       return ocorrenciaRepository.findById(id).orElseThrow(()->
                new IDNaoEncontradoException("A ocorrência com ID " + id + " não foi encontrado."));
    }

    @Override
    public List<Ocorrencia> obterLista() {

        return ocorrenciaRepository.findAll();
    }

    @Override
    public void excluir(Integer id) {

        if(id == null || id <= 0) {
            throw new IllegalArgumentException("" +
                    " ID para exclusão não pode ser nulo e deve ser maio que zero.");
        }

        Ocorrencia ocorrencia = obterPorId(id);
        ocorrenciaRepository.delete(ocorrencia);
    }

}
