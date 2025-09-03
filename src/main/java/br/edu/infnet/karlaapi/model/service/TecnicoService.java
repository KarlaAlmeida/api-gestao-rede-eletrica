package br.edu.infnet.karlaapi.model.service;

import br.edu.infnet.karlaapi.model.domain.entities.Tecnico;
import br.edu.infnet.karlaapi.model.infraestructure.exceptions.ResourceNotFoundException;
import br.edu.infnet.karlaapi.model.repository.TecnicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TecnicoService implements CrudService<Tecnico, Integer>{

    private final TecnicoRepository tecnicoRepository;

    public TecnicoService(TecnicoRepository tecnicoRepository) {
        this.tecnicoRepository = tecnicoRepository;
    }

    @Override
    public Tecnico incluir(Tecnico tecnico) {
        return tecnicoRepository.save(tecnico);
    }

    @Override
    public Tecnico alterar(Integer id, Tecnico tecnicoAtualizado) {
        obterPorId(id);
        tecnicoAtualizado.setId(id);
        return tecnicoRepository.save(tecnicoAtualizado);
    }

    public Tecnico inativar(Integer id) {
        Tecnico tecnico = obterPorId(id);

        if(!tecnico.isAtivo()) {
            System.out.println("Técnico " + tecnico.getNome() + " já está inativo!");
            return tecnico;
        }
        tecnico.setAtivo(false);

        return tecnicoRepository.save(tecnico);
    }

    @Override
    public Tecnico obterPorId(Integer id) {
        return tecnicoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("O tecnico com ID " + id + " não foi encontrado."));
    }

    @Override
    public List<Tecnico> obterLista() {
        return tecnicoRepository.findAll();
    }

    public List<Tecnico> buscarPorNomeEspecialidade(String nomePrefixo, String especialidade) {
        return tecnicoRepository.
                findByNomeStartingWithIgnoreCaseAndEspecialidadeIgnoreCase(
                        nomePrefixo, especialidade);
    }

    @Override
    public void excluir(Integer id) {
        Tecnico tecnico = obterPorId(id);
        tecnicoRepository.delete(tecnico);
    }

}
