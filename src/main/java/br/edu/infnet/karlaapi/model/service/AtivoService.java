package br.edu.infnet.karlaapi.model.service;

import br.edu.infnet.karlaapi.model.domain.entities.Ativo;
import br.edu.infnet.karlaapi.model.domain.enums.StatusAtivo;
import br.edu.infnet.karlaapi.model.domain.exceptions.AtributoInvalidoException;
import br.edu.infnet.karlaapi.model.domain.exceptions.IDNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AtivoService implements CrudService<Ativo, Integer>{

    private final Map<Integer, Ativo> mapa = new ConcurrentHashMap<Integer, Ativo>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    private void validar(Ativo ativo) {
        if(ativo == null) {
            throw new IllegalArgumentException("O ativo não pode estar nulo!");
        }

        if(ativo.getTipoAtivo() == null) {
            throw new AtributoInvalidoException("O tipo do ativo é uma informação obrigatória!");
        }
    }

    @Override
    public Ativo incluir(Ativo ativo) {
        validar(ativo);

        if(ativo.getId() != null && ativo.getId() != 0) {
            throw new IllegalArgumentException("Um novo ativo não pode ter um ID na inclusão!");
        }

        ativo.setId(nextId.getAndIncrement());
        mapa.put(ativo.getId(), ativo);
        return ativo;
    }

    @Override
    public Ativo alterar(Integer id, Ativo ativo) {
        if(id == null || id == 0) {
            throw new IllegalArgumentException("O ID para alteração não pode ser nulo/zero!");
        }

        validar(ativo);
        obterPorId(id);
        ativo.setId(id);
        mapa.put(ativo.getId(), ativo);
        return ativo;
    }

    public Ativo alterarStatus(Integer id, StatusAtivo status){
        Ativo ativo = mapa.get(id);

        if(ativo == null) {
            throw new IllegalArgumentException("Não foi possível obter o ativo pelo ID " + id);
        }

        if(status.equals(ativo.getStatusAtivo())){
            throw new IllegalStateException("O status atual do ativo já é " + status);
        }

        ativo.setStatusAtivo(status);
        return ativo;
    }

    @Override
    public Ativo obterPorId(Integer id) {
        Ativo ativo = mapa.get(id);

        if(ativo == null) {
            throw new IllegalArgumentException("Não foi possível obter o ativo pelo ID " + id);
        }
        return ativo;
    }

    @Override
    public List<Ativo> obterLista() {

        return new ArrayList<Ativo>(mapa.values());
    }

    @Override
    public void excluir(Integer id) {

        if(id == null || id == 0) {
            throw new IllegalArgumentException("O ID para exclusão não pode ser nulo/zero!");
        }

        if(!mapa.containsKey(id)) {
            throw new IDNaoEncontradoException("O ativo com ID " + id + " não foi encontrado!");
        }

        mapa.remove(id);
    }

}
