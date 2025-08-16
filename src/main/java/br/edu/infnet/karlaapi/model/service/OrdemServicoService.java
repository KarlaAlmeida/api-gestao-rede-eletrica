package br.edu.infnet.karlaapi.model.service;

import br.edu.infnet.karlaapi.model.domain.entities.OrdemServico;
import br.edu.infnet.karlaapi.model.infraestructure.enums.StatusOS;
import br.edu.infnet.karlaapi.model.infraestructure.exceptions.AtributoInvalidoException;
import br.edu.infnet.karlaapi.model.infraestructure.exceptions.IDNaoEncontradoException;
import br.edu.infnet.karlaapi.model.repository.OrdemServicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdemServicoService implements CrudService<OrdemServico, Integer>{

    private final OrdemServicoRepository ordemServicoRepository;

    public OrdemServicoService(OrdemServicoRepository ordemServicoRepository) {
        this.ordemServicoRepository = ordemServicoRepository;
    }

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
        return ordemServicoRepository.save(ordemServico);
    }

    @Override
    public OrdemServico alterar(Integer id, OrdemServico ordemServico) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "O ID para alteração não pode ser nulo e deve ser maio que zero.");
        }

        validar(ordemServico);
        obterPorId(id);
        ordemServico.setId(id);
        return ordemServicoRepository.save(ordemServico);
    }

    public OrdemServico alterarStatus(Integer id, StatusOS status){
        OrdemServico ordemServico = obterPorId(id);

        if(ordemServico == null) {
            throw new IllegalArgumentException("Não foi possível obter a ordem de serviço pelo ID " + id);
        }

        if(status.equals(ordemServico.getStatusOS())){
            throw new IllegalStateException("O status atual da ordem de serviço já é " + status);
        }

        ordemServico.setStatusOS(status);
        return ordemServicoRepository.save(ordemServico);
    }

    @Override
    public OrdemServico obterPorId(Integer id) {
        return ordemServicoRepository.findById(id).orElseThrow(()->
                new IDNaoEncontradoException("A ordem de serviço com ID " + id + " não foi encontrado."));
    }

    @Override
    public List<OrdemServico> obterLista() {

        return ordemServicoRepository.findAll();
    }

    @Override
    public void excluir(Integer id) {

        if(id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "ID para exclusão não pode ser nulo e deve ser maio que zero.");
        }

        OrdemServico ordemServico = obterPorId(id);
        ordemServicoRepository.delete(ordemServico);
    }

}
