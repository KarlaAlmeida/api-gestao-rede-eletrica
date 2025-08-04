package br.edu.infnet.karlaapi.model.service;

import br.edu.infnet.karlaapi.model.domain.entities.Tecnico;
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

    public Tecnico obter(){
        Tecnico tecnico = new Tecnico();
        tecnico.setId(1);
        tecnico.setNome("Karla");
        tecnico.setCpf("123456789-00");
        tecnico.setEmail("karlacrika@gmail.com");
        tecnico.setTelefone("83996220199");
        tecnico.setUltimoSalario(5000.00);
        tecnico.setEhAtivo(true);
        tecnico.setEspecialidade("Elétrica");
        tecnico.setDisponivel(true);
        return tecnico;
    }

    @Override
    public Tecnico salvar(Tecnico tecnico) {
        tecnico.setId(nextId.getAndIncrement());
        mapa.put(tecnico.getId(), tecnico);
        return tecnico;
    }

    @Override
    public List obterLista() {
        return new ArrayList<Tecnico>(mapa.values());
    }

    @Override
    public void excluir(Integer id) {
        mapa.remove(id);
    }

}
