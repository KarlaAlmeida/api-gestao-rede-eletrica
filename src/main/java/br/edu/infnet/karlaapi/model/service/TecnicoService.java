package br.edu.infnet.karlaapi.model.service;

import br.edu.infnet.karlaapi.model.domain.entities.Tecnico;
import br.edu.infnet.karlaapi.model.infraestructure.exceptions.AtributoInvalidoException;
import br.edu.infnet.karlaapi.model.infraestructure.exceptions.IDNaoEncontradoException;
import br.edu.infnet.karlaapi.model.repository.TecnicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TecnicoService implements CrudService<Tecnico, Integer>{

    private final TecnicoRepository tecnicoRepository;

    public TecnicoService(TecnicoRepository tecnicoRepository) {
        this.tecnicoRepository = tecnicoRepository;
    }

    private void validar(Tecnico tecnico) {
        if(tecnico == null) {
            throw new IllegalArgumentException("O técnico não pode estar nulo!");
        }

        if(tecnico.getNome() == null || tecnico.getNome().trim().isEmpty()) {
            throw new AtributoInvalidoException("O nome do técnico é uma informação obrigatória!");
        }
    }

    @Override
    public Tecnico incluir(Tecnico tecnico) {
        validar(tecnico);

        if(tecnico.getId() != null && tecnico.getId() != 0) {
            throw new IllegalArgumentException("Um novo técnico não pode ter um ID na inclusão!");
        }
        
        return tecnicoRepository.save(tecnico);
    }

    @Override
    public Tecnico alterar(Integer id, Tecnico tecnicoAtualizado) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("" +
                    "O ID para alteração nnão pode ser nulo e deve ser maio que zero.");
        }

        validar(tecnicoAtualizado);
        obterPorId(id);
        tecnicoAtualizado.setId(id);
        return tecnicoRepository.save(tecnicoAtualizado);
    }

    public Tecnico inativar(Integer id) {

        if(id == null || id <= 0) {
            throw new IllegalArgumentException("" +
                    "O ID para inativação não pode ser nulo e deve ser maio que zero.");
        }

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
                new IDNaoEncontradoException("O tecnico com ID " + id + " não foi encontrado."));
    }

    @Override
    public List<Tecnico> obterLista() {

        return tecnicoRepository.findAll();
    }

    @Override
    public void excluir(Integer id) {

        if(id == null || id <= 0) {
            throw new IllegalArgumentException("" +
                    "O ID para exclusão não pode ser nulo e deve ser maio que zero.");
        }

        Tecnico tecnico = obterPorId(id);
        tecnicoRepository.delete(tecnico);
    }

}
