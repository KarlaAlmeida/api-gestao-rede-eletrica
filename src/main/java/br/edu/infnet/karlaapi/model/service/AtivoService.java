package br.edu.infnet.karlaapi.model.service;

import br.edu.infnet.karlaapi.model.domain.entities.Ativo;
import br.edu.infnet.karlaapi.model.infraestructure.enums.StatusAtivo;
import br.edu.infnet.karlaapi.model.infraestructure.exceptions.ResourceNotFoundException;
import br.edu.infnet.karlaapi.model.repository.AtivoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtivoService implements CrudService<Ativo, Integer>{

    private final AtivoRepository ativoRepository;

    public AtivoService(AtivoRepository ativoRepository) {
        this.ativoRepository = ativoRepository;
    }

    public Ativo incluir(Ativo ativo) {
        return ativoRepository.save(ativo);
    }

    @Override
    public Ativo alterar(Integer id, Ativo ativoAtualizado) {
        obterPorId(id);
        ativoAtualizado.setId(id);
        return ativoRepository.save(ativoAtualizado);
    }

    public Ativo alterarStatus(Integer id, String status){
        Ativo ativo = obterPorId(id);

        StatusAtivo statusNovo = StatusAtivo.fromString(status);

        if(statusNovo.equals(ativo.getStatusAtivo())){
            throw new IllegalStateException("O status atual do ativo já é " + status);
        }

        ativo.setStatusAtivo(statusNovo);
        return ativoRepository.save(ativo);
    }

    @Override
    public Ativo obterPorId(Integer id) {
        return ativoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("O ativo com ID " + id + " não foi encontrado."));
    }

    @Override
    public List<Ativo> obterLista() {
        return ativoRepository.findAll();
    }

    @Override
    public void excluir(Integer id) {
        Ativo ativo = obterPorId(id);
        ativoRepository.delete(ativo);
    }

}
