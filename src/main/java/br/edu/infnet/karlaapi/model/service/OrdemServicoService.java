package br.edu.infnet.karlaapi.model.service;

import br.edu.infnet.karlaapi.model.domain.dto.in.OrdemServicoAlteraDataConclusaoDTO;
import br.edu.infnet.karlaapi.model.domain.dto.in.OrdemServicoRequestDTO;
import br.edu.infnet.karlaapi.model.domain.entities.Ocorrencia;
import br.edu.infnet.karlaapi.model.domain.entities.OrdemServico;
import br.edu.infnet.karlaapi.model.domain.entities.Tecnico;
import br.edu.infnet.karlaapi.model.infraestructure.enums.StatusOS;
import br.edu.infnet.karlaapi.model.infraestructure.exceptions.ResourceNotFoundException;
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

    public OrdemServico incluir(OrdemServicoRequestDTO dto) {

        Ocorrencia ocorrencia = obterOcorrenciaPorId(dto.getOcorrenciaId());
        Tecnico tecnico = obterTecnicoPorCPF(dto.getCpfTecnico());

        OrdemServico ordemServico = new OrdemServico();
        ordemServico.setOcorrencia(ocorrencia);
        ordemServico.setTecnico(tecnico);
        ordemServico.setDataCriacaoOS(LocalDate.now());
        ordemServico.setDescricaoServico(dto.getDescricaoServico());
        ordemServico.setStatusOS(StatusOS.ABERTA);

        return ordemServicoRepository.save(ordemServico);
    }

    public OrdemServico alterar(Integer id, OrdemServicoRequestDTO dto) {

        OrdemServico ordemServico = obterPorId(id);
        ordemServico.setId(id);
        Ocorrencia ocorrencia = obterOcorrenciaPorId(dto.getOcorrenciaId());
        ocorrencia.setId(id);
        Tecnico tecnico = obterTecnicoPorCPF(dto.getCpfTecnico());
        tecnico.setId(id);

        ordemServico.setOcorrencia(ocorrencia);
        ordemServico.setTecnico(tecnico);
        ordemServico.setDescricaoServico(dto.getDescricaoServico());

        return ordemServicoRepository.save(ordemServico);
    }

    public OrdemServico alterarStatus(Integer id, String statusNovo){
        OrdemServico ordemServico = obterPorId(id);

        StatusOS statusOSNovo = StatusOS.fromString(statusNovo);

        if(statusOSNovo.equals(ordemServico.getStatusOS())){
            throw new IllegalStateException("O status atual da ordem de serviço já é " + statusOSNovo);
        }

        ordemServico.setStatusOS(statusOSNovo);
        return ordemServicoRepository.save(ordemServico);
    }

    public OrdemServico alterarDataConclusao(Integer id, OrdemServicoAlteraDataConclusaoDTO dataConclusaoDTO){
        OrdemServico ordemServico = obterPorId(id);

        if(dataConclusaoDTO.equals(ordemServico.getDataConclusaoOS())){
            throw new IllegalStateException(
                    "A data de conclusão atual da ordem de serviço já é " + dataConclusaoDTO);
        }

        ordemServico.setDataConclusaoOS(dataConclusaoDTO.getDataConclusaoOS());
        return ordemServicoRepository.save(ordemServico);
    }

    public OrdemServico obterPorId(Integer id) {
        return ordemServicoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("A ordem de serviço com ID " + id + " não foi encontrada."));
    }

    public Ocorrencia obterOcorrenciaPorId(Integer id){
        return ocorrenciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ocorrência não encontrada pelo ID " + id));
    }

    public Tecnico obterTecnicoPorCPF(String cpfTecnico){
        return tecnicoRepository.findByCpf(cpfTecnico)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Técnico não encontrado pelo CPF " + cpfTecnico));
    }

    public List<OrdemServico> obterLista() {
        return ordemServicoRepository.findAll();
    }

    public List<OrdemServico> listarPorTecnico(String cpf) {
        return ordemServicoRepository.findByTecnicoCpf(cpf);
    }

    public List<OrdemServico> filtrarPorDescricaoServicoEPeriodo(
            String descricaoBusca, LocalDate dataInicio, LocalDate dataFim) {
        return ordemServicoRepository
                .findByDescricaoServicoContainingIgnoreCaseAndDataCriacaoOSBetween(
                        descricaoBusca, dataInicio, dataFim);
    }

    public void excluir(Integer id) {
        OrdemServico ordemServico = obterPorId(id);
        ordemServicoRepository.delete(ordemServico);
    }

}
