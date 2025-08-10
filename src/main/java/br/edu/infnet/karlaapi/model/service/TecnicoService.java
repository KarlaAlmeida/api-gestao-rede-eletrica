package br.edu.infnet.karlaapi.model.service;

import br.edu.infnet.karlaapi.model.domain.entities.Tecnico;
import br.edu.infnet.karlaapi.model.domain.exceptions.TecnicoInvalidoException;
import br.edu.infnet.karlaapi.model.domain.exceptions.IDNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TecnicoService implements CrudService<Tecnico, Integer>{

    private final Map<Integer, Tecnico> mapa = new ConcurrentHashMap<Integer, Tecnico>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    private void validar(Tecnico tecnico) {
        if(tecnico == null) {
            throw new IllegalArgumentException("O técnico não pode estar nulo!");
        }

        if(tecnico.getNome() == null || tecnico.getNome().trim().isEmpty()) {
            throw new TecnicoInvalidoException("O nome do técnico é uma informação obrigatória!");
        }
    }

    @Override
    public Tecnico incluir(Tecnico tecnico) {
        validar(tecnico);

        if(tecnico.getId() != null && tecnico.getId() != 0) {
            throw new IllegalArgumentException("Um novo técnico não pode ter um ID na inclusão!");
        }

        tecnico.setId(nextId.getAndIncrement());
        mapa.put(tecnico.getId(), tecnico);
        return tecnico;
    }

    @Override
    public Tecnico alterar(Integer id, Tecnico tecnico) {
        if(id == null || id == 0) {
            throw new IllegalArgumentException("O ID para alteração não pode ser nulo/zero!");
        }

        validar(tecnico);
        obterPorId(id);
        tecnico.setId(id);
        mapa.put(tecnico.getId(), tecnico);
        return tecnico;
    }

    public Tecnico inativar(Integer id) {

        if(id == null || id == 0) {
            throw new IllegalArgumentException("O ID para inativação não pode ser nulo/zero!");
        }

        Tecnico tecnico = obterPorId(id);

        if(!tecnico.isEhAtivo()) {
            System.out.println("Técnico " + tecnico.getNome() + " já está inativo!");
            return tecnico;
        }

        tecnico.setEhAtivo(false);
        mapa.put(tecnico.getId(), tecnico);
        return tecnico;
    }

    @Override
    public Tecnico obterPorId(Integer id) {
        Tecnico tecnico = mapa.get(id);

        if(tecnico == null) {
            throw new IllegalArgumentException("Não foi possível obter o vendedor pelo ID " + id);
        }
        return tecnico;
    }

    @Override
    public List<Tecnico> obterLista() {

        return new ArrayList<Tecnico>(mapa.values());
    }

    @Override
    public void excluir(Integer id) {

        if(id == null || id == 0) {
            throw new IllegalArgumentException("O ID para exclusão não pode ser nulo/zero!");
        }

        if(!mapa.containsKey(id)) {
            throw new IDNaoEncontradoException("O técnico com ID " + id + " não foi encontrado!");
        }

        mapa.remove(id);
    }

}
