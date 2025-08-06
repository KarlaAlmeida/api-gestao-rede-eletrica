package br.edu.infnet.karlaapi.model.service;

import br.edu.infnet.karlaapi.model.domain.entities.Tecnico;
import br.edu.infnet.karlaapi.model.domain.exceptions.TecnicoInvalidoException;
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


    @Override
    public Tecnico salvar(Tecnico tecnico) {
        if(tecnico.getNome() == null) {
            throw new TecnicoInvalidoException("O nome do técnico é uma informação obrigatória!");
        }

        tecnico.setId(nextId.getAndIncrement());
        mapa.put(tecnico.getId(), tecnico);
        return tecnico;
    }

    @Override
    public Tecnico obterporId(Integer id) {
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
        mapa.remove(id);
    }

}
