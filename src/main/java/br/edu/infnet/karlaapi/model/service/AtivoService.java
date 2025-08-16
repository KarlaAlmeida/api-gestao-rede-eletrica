package br.edu.infnet.karlaapi.model.service;

import br.edu.infnet.karlaapi.model.domain.entities.Ativo;
import br.edu.infnet.karlaapi.model.infraestructure.enums.StatusAtivo;
import br.edu.infnet.karlaapi.model.infraestructure.exceptions.AtributoInvalidoException;
import br.edu.infnet.karlaapi.model.infraestructure.exceptions.IDNaoEncontradoException;
import br.edu.infnet.karlaapi.model.repository.AtivoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtivoService implements CrudService<Ativo, Integer>{

    private final AtivoRepository ativoRepository;

    public AtivoService(AtivoRepository ativoRepository) {
        this.ativoRepository = ativoRepository;
    }

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

        return ativoRepository.save(ativo);
    }

    @Override
    public Ativo alterar(Integer id, Ativo ativoAtualizado) {

        if(id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "O ID para exclusão não pode ser nulo e deve ser maio que zero.");
        }

        validar(ativoAtualizado);
        obterPorId(id);
        ativoAtualizado.setId(id);
        return ativoRepository.save(ativoAtualizado);
    }

    public Ativo alterarStatus(Integer id, StatusAtivo status){
        Ativo ativo = obterPorId(id);

        if(ativo == null) {
            throw new IllegalArgumentException("Não foi possível obter o ativo pelo ID " + id);
        }

        if(status.equals(ativo.getStatusAtivo())){
            throw new IllegalStateException("O status atual do ativo já é " + status);
        }

        ativo.setStatusAtivo(status);
        return ativoRepository.save(ativo);
    }

    @Override
    public Ativo obterPorId(Integer id) {

        return ativoRepository.findById(id).orElseThrow(()->
                new IDNaoEncontradoException("O ativo com ID " + id + " não foi encontrado."));
    }

    @Override
    public List<Ativo> obterLista() {

        return ativoRepository.findAll();
    }

    @Override
    public void excluir(Integer id) {

        if(id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "O ID para exclusão não pode ser nulo e deve ser maio que zero.");
        }

        Ativo ativo = obterPorId(id);
        ativoRepository.delete(ativo);

    }

}
