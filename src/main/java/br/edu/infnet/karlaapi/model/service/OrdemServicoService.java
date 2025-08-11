package br.edu.infnet.karlaapi.model.service;

import br.edu.infnet.karlaapi.model.domain.entities.OrdemServico;
import br.edu.infnet.karlaapi.model.domain.enums.StatusOS;
import br.edu.infnet.karlaapi.model.domain.exceptions.AtributoInvalidoException;
import br.edu.infnet.karlaapi.model.domain.exceptions.IDNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrdemServicoService implements CrudService<OrdemServico, Integer>{

    private final Map<Integer, OrdemServico> mapa = new ConcurrentHashMap<Integer, OrdemServico>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    private void validar(OrdemServico ordemServico) {
        if(ordemServico == null) {
            throw new IllegalArgumentException("A ordem de serviço não pode estar nula!");
        }

        if(ordemServico.getTecnico() == null ||
                ordemServico.getOcorrencia() == null ||
                ordemServico.getDescricaoServico() == null ||
                ordemServico.getDataCriacaoOS() == null ||
                ordemServico.getStatusOS() == null) {
            throw new AtributoInvalidoException(
                    "Todas as informações devem ser preenchidas, exceto data de conclusão!");
        }
    }

    @Override
    public OrdemServico incluir(OrdemServico ordemServico) {
        validar(ordemServico);

        if(ordemServico.getId() != null && ordemServico.getId() != 0) {
            throw new IllegalArgumentException(
                    "Uma nova ordem de serviço não pode ter um ID na inclusão!");
        }

        ordemServico.setId(nextId.getAndIncrement());
        mapa.put(ordemServico.getId(), ordemServico);
        return ordemServico;
    }

    @Override
    public OrdemServico alterar(Integer id, OrdemServico ordemServico) {
        if(id == null || id == 0) {
            throw new IllegalArgumentException("O ID para alteração não pode ser nulo/zero!");
        }

        validar(ordemServico);
        obterPorId(id);
        ordemServico.setId(id);
        mapa.put(ordemServico.getId(), ordemServico);
        return ordemServico;
    }

    public OrdemServico alterarStatus(Integer id, StatusOS status){
        OrdemServico ordemServico = mapa.get(id);

        if(ordemServico == null) {
            throw new IllegalArgumentException("Não foi possível obter a ordem de serviço pelo ID " + id);
        }

        if(status.equals(ordemServico.getStatusOS())){
            throw new IllegalStateException("O status atual da ordem de serviço já é " + status);
        }

        ordemServico.setStatusOS(status);
        return ordemServico;
    }

    @Override
    public OrdemServico obterPorId(Integer id) {
        OrdemServico ordemServico = mapa.get(id);

        if(ordemServico == null) {
            throw new IllegalArgumentException("Não foi possível obter a ordem de serviço pelo ID " + id);
        }
        return ordemServico;
    }

    @Override
    public List<OrdemServico> obterLista() {

        return new ArrayList<OrdemServico>(mapa.values());
    }

    @Override
    public void excluir(Integer id) {

        if(id == null || id == 0) {
            throw new IllegalArgumentException("O ID para exclusão não pode ser nulo/zero!");
        }

        if(!mapa.containsKey(id)) {
            throw new IDNaoEncontradoException("A ordem de serviço com ID " + id + " não foi encontrado!");
        }

        mapa.remove(id);
    }

}
