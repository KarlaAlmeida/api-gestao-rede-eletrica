package br.edu.infnet.karlaapi.model.service;

import br.edu.infnet.karlaapi.model.domain.dto.in.AtivoRequestDTO;
import br.edu.infnet.karlaapi.model.domain.entities.Ativo;
import br.edu.infnet.karlaapi.model.domain.entities.Endereco;
import br.edu.infnet.karlaapi.model.infraestructure.enums.StatusAtivo;
import br.edu.infnet.karlaapi.model.infraestructure.enums.TipoAtivo;
import br.edu.infnet.karlaapi.model.infraestructure.exceptions.ResourceNotFoundException;
import br.edu.infnet.karlaapi.model.repository.AtivoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtivoService{

    private final AtivoRepository ativoRepository;

    public AtivoService(AtivoRepository ativoRepository) {
        this.ativoRepository = ativoRepository;
    }

    public Ativo incluir(AtivoRequestDTO ativoDTO) {

        Ativo ativo = new Ativo();
        ativo.setTipoAtivo(TipoAtivo.fromString(ativoDTO.getTipoAtivo()));
        ativo.setDataInstalacao(ativoDTO.getDataInstalacao());
        ativo.setStatusAtivo(StatusAtivo.ATIVO);

        Endereco endereco = ativoDTO.getEndereco();
        ativo.setEndereco(endereco);

        return ativoRepository.save(ativo);
    }

    public Ativo alterar(Integer id, AtivoRequestDTO ativoDTO) {

        Ativo ativo = obterPorId(id);
        ativo.setId(id);
        ativo.setTipoAtivo(TipoAtivo.fromString(ativoDTO.getTipoAtivo()));
        ativo.setDataInstalacao(ativoDTO.getDataInstalacao());

        Endereco endereco = ativoDTO.getEndereco();
        ativo.setEndereco(endereco);

        return ativoRepository.save(ativo);
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

    public Ativo obterPorId(Integer id) {
        return ativoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("O ativo com ID " + id + " não foi encontrado."));
    }

    public List<Ativo> obterLista() {
        return ativoRepository.findAll();
    }

    public void excluir(Integer id) {
        Ativo ativo = obterPorId(id);
        ativoRepository.delete(ativo);
    }

}
