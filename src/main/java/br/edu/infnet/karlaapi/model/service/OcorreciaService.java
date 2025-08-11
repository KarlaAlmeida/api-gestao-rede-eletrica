package br.edu.infnet.karlaapi.model.service;

import br.edu.infnet.karlaapi.model.domain.entities.Ocorrencia;
import br.edu.infnet.karlaapi.model.domain.enums.PrioridadeOcorrecia;
import br.edu.infnet.karlaapi.model.domain.enums.StatusOcorrecia;
import br.edu.infnet.karlaapi.model.domain.exceptions.AtributoInvalidoException;
import br.edu.infnet.karlaapi.model.domain.exceptions.IDNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OcorreciaService implements CrudService<Ocorrencia, Integer>{

    private final Map<Integer, Ocorrencia> mapa = new ConcurrentHashMap<Integer, Ocorrencia>();
    private final AtomicInteger nextId = new AtomicInteger(1);

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

        ocorrencia.setId(nextId.getAndIncrement());
        mapa.put(ocorrencia.getId(), ocorrencia);
        return ocorrencia;
    }

    @Override
    public Ocorrencia alterar(Integer id, Ocorrencia ocorrencia) {
        if(id == null || id == 0) {
            throw new IllegalArgumentException("O ID para alteração não pode ser nulo/zero!");
        }

        validar(ocorrencia);
        obterPorId(id);
        ocorrencia.setId(id);
        mapa.put(ocorrencia.getId(), ocorrencia);
        return ocorrencia;
    }

    public Ocorrencia alterarStatus(Integer id, StatusOcorrecia status){
        Ocorrencia ocorrencia = mapa.get(id);

        if(ocorrencia == null) {
            throw new IllegalArgumentException("Não foi possível obter a ocorrência pelo ID " + id);
        }

        if(status.equals(ocorrencia.getStatusOcorrecia())){
            throw new IllegalStateException("O status atual da ocorrência já é " + status);
        }

        ocorrencia.setStatusOcorrecia(status);
        return ocorrencia;
    }

    public Ocorrencia alterarPrioridade(Integer id, PrioridadeOcorrecia prioridade){
        Ocorrencia ocorrencia = mapa.get(id);

        if(ocorrencia == null) {
            throw new IllegalArgumentException("Não foi possível obter a ocorrência pelo ID " + id);
        }

        if(prioridade.equals(ocorrencia.getPrioridadeOcorrecia())){
            throw new IllegalStateException("A prioridade atual da ocorrência já é " + prioridade);
        }

        ocorrencia.setPrioridadeOcorrecia(prioridade);
        return ocorrencia;
    }

    @Override
    public Ocorrencia obterPorId(Integer id) {
        Ocorrencia ocorrencia = mapa.get(id);

        if(ocorrencia == null) {
            throw new IllegalArgumentException("Não foi possível obter a ocorrência pelo ID " + id);
        }
        return ocorrencia;
    }

    @Override
    public List<Ocorrencia> obterLista() {

        return new ArrayList<Ocorrencia>(mapa.values());
    }

    @Override
    public void excluir(Integer id) {

        if(id == null || id == 0) {
            throw new IllegalArgumentException("O ID para exclusão não pode ser nulo/zero!");
        }

        if(!mapa.containsKey(id)) {
            throw new IDNaoEncontradoException("A ocorrência com ID " + id + " não foi encontrado!");
        }

        mapa.remove(id);
    }

}
