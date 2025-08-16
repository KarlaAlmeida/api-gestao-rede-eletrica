package br.edu.infnet.karlaapi.model.service;

import br.edu.infnet.karlaapi.model.domain.dto.OrdemServicoRequestDTO;
import br.edu.infnet.karlaapi.model.domain.entities.Ocorrencia;
import br.edu.infnet.karlaapi.model.domain.entities.OrdemServico;
import br.edu.infnet.karlaapi.model.domain.entities.Tecnico;
import br.edu.infnet.karlaapi.model.infraestructure.enums.StatusOS;
import br.edu.infnet.karlaapi.model.infraestructure.exceptions.AtributoInvalidoException;
import br.edu.infnet.karlaapi.model.infraestructure.exceptions.IDNaoEncontradoException;
import br.edu.infnet.karlaapi.model.repository.OcorrenciaRepository;
import br.edu.infnet.karlaapi.model.repository.OrdemServicoRepository;
import br.edu.infnet.karlaapi.model.repository.TecnicoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrdemServicoService {

    private final OrdemServicoRepository ordemServicoRepository;
    private final OcorrenciaRepository ocorrenciaRepository;
    private final TecnicoRepository tecnicoRepository;

    public OrdemServicoService(OrdemServicoRepository ordemServicoRepository,
                               OcorrenciaRepository ocorrenciaRepository,
                               TecnicoRepository tecnicoRepository) {
        this.ordemServicoRepository = ordemServicoRepository;
        this.ocorrenciaRepository = ocorrenciaRepository;
        this.tecnicoRepository = tecnicoRepository;
    }

    private void validar(OrdemServicoRequestDTO dto) {
        if(dto == null) {
            throw new IllegalArgumentException("A ordem de serviço não pode estar nula!");
        }

        if(dto.getOcorrenciaId() == null ||
                dto.getTecnicoId() == null ||
                dto.getDescricaoServico() == null) {
            throw new AtributoInvalidoException("Todas as informações devem ser preenchidas!");
        }
    }

    public OrdemServico incluir(OrdemServicoRequestDTO dto) {
        validar(dto);

        Ocorrencia ocorrencia = ocorrenciaRepository.findById(dto.getOcorrenciaId())
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada"));

        Tecnico tecnico = tecnicoRepository.findById(dto.getTecnicoId())
                .orElseThrow(() -> new RuntimeException("Técnico não encontrado"));

        OrdemServico ordemServico = new OrdemServico();
        ordemServico.setOcorrencia(ocorrencia);
        ordemServico.setTecnico(tecnico);
        ordemServico.setDataCriacaoOS(LocalDate.now());
        ordemServico.setDescricaoServico(dto.getDescricaoServico());
        ordemServico.setStatusOS(StatusOS.ABERTA);

        return ordemServicoRepository.save(ordemServico);
    }

    public OrdemServico alterar(Integer id, OrdemServicoRequestDTO dto) {
        validar(dto);

        OrdemServico ordemServico = ordemServicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ordem de Serviço não encontrada."));

        Integer tecnicoId = dto.getTecnicoId();

        Tecnico tecnico = tecnicoRepository.findById(tecnicoId)
                .orElseThrow(() -> new RuntimeException("Técnico não encontrado."));

        ordemServico.setTecnico(tecnico);
        ordemServico.setDescricaoServico(dto.getDescricaoServico());

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

    public OrdemServico alterarDataConclusao(Integer id, LocalDate dataConclusao){
        OrdemServico ordemServico = obterPorId(id);

        if(ordemServico == null) {
            throw new IllegalArgumentException("Não foi possível obter a ordem de serviço pelo ID " + id);
        }

        if(dataConclusao.equals(ordemServico.getDataConclusaoOS())){
            throw new IllegalStateException("A data de conclusão atual da ordem de serviço já é " + dataConclusao);
        }

        ordemServico.setDataConclusaoOS(dataConclusao);
        return ordemServicoRepository.save(ordemServico);
    }

    public OrdemServico obterPorId(Integer id) {
        return ordemServicoRepository.findById(id).orElseThrow(()->
                new IDNaoEncontradoException("A ordem de serviço com ID " + id + " não foi encontrado."));
    }

    public List<OrdemServico> obterLista() {
        return ordemServicoRepository.findAll();
    }

    public void excluir(Integer id) {
        OrdemServico ordemServico = ordemServicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ordem de Serviço não encontrada."));

        ordemServicoRepository.delete(ordemServico);
    }

}
