package br.edu.infnet.karlaapi.model.service;

import br.edu.infnet.karlaapi.model.domain.dto.in.OrdemServicoAlteraDataConclusaoDTO;
import br.edu.infnet.karlaapi.model.domain.dto.in.OrdemServicoRequestDTO;
import br.edu.infnet.karlaapi.model.domain.dto.out.OcorrenciaResponseDTO;
import br.edu.infnet.karlaapi.model.domain.dto.out.OrdemServicoResponseDTO;
import br.edu.infnet.karlaapi.model.domain.dto.out.TecnicoResponseDTO;
import br.edu.infnet.karlaapi.model.domain.entities.OrdemServico;
import br.edu.infnet.karlaapi.model.infraestructure.enums.StatusOS;
import br.edu.infnet.karlaapi.model.infraestructure.exceptions.ResourceNotFoundException;
import br.edu.infnet.karlaapi.model.repository.OcorrenciaRepository;
import br.edu.infnet.karlaapi.model.repository.OrdemServicoRepository;
import br.edu.infnet.karlaapi.model.repository.TecnicoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public OrdemServicoResponseDTO incluir(OrdemServicoRequestDTO dto) {

        OcorrenciaResponseDTO ocorrenciaResponseDTO = obterOcorrenciaPorId(dto.getOcorrenciaId());
        TecnicoResponseDTO tecnicoResponseDTO = obterTecnicoPorCPF(dto.getCpfTecnico());

        OrdemServicoResponseDTO ordemServicoResponseDTO = new OrdemServicoResponseDTO();
        ordemServicoResponseDTO.setOcorrencia(ocorrenciaResponseDTO);
        ordemServicoResponseDTO.setTecnico(tecnicoResponseDTO);
        ordemServicoResponseDTO.setDataCriacaoOS(LocalDate.now());
        ordemServicoResponseDTO.setDescricaoServico(dto.getDescricaoServico());
        ordemServicoResponseDTO.setStatusOS(StatusOS.ABERTA);

        OrdemServico ordemServico = new OrdemServico(ordemServicoResponseDTO);

        return new OrdemServicoResponseDTO(ordemServicoRepository.save(ordemServico));
    }

    public OrdemServicoResponseDTO alterar(Integer id, OrdemServicoRequestDTO dto) {

        OrdemServicoResponseDTO ordemServicoResponseDTO = obterPorId(id);

        if(dto.getOcorrenciaId() != null) {
            OcorrenciaResponseDTO ocorrenciaResponseDTO = obterOcorrenciaPorId(dto.getOcorrenciaId());
            ordemServicoResponseDTO.setOcorrencia(ocorrenciaResponseDTO);
        }

        if(dto.getCpfTecnico() != null) {
            TecnicoResponseDTO tecnicoResponseDTO = obterTecnicoPorCPF(dto.getCpfTecnico());
            ordemServicoResponseDTO.setTecnico(tecnicoResponseDTO);
        }

        if(dto.getDescricaoServico() != null) ordemServicoResponseDTO.setDescricaoServico(dto.getDescricaoServico());

        OrdemServico ordemServico = new OrdemServico(ordemServicoResponseDTO);

        return new OrdemServicoResponseDTO(ordemServicoRepository.save(ordemServico));
    }

    public OrdemServicoResponseDTO alterarStatus(Integer id, String statusNovo){
        OrdemServicoResponseDTO ordemServicoResponseDTO = obterPorId(id);

        StatusOS statusOSNovo = StatusOS.fromString(statusNovo);

        if(statusOSNovo.equals(ordemServicoResponseDTO.getStatusOS())){
            throw new IllegalStateException("O status atual da ordem de serviço já é " + statusOSNovo);
        }

        ordemServicoResponseDTO.setStatusOS(statusOSNovo);

        OrdemServico ordemServico = new OrdemServico(ordemServicoResponseDTO);

        return new OrdemServicoResponseDTO(ordemServicoRepository.save(ordemServico));
    }

    public OrdemServicoResponseDTO alterarDataConclusao(Integer id, OrdemServicoAlteraDataConclusaoDTO dataConclusaoDTO){
        OrdemServicoResponseDTO ordemServicoResponseDTO = obterPorId(id);

        if(dataConclusaoDTO.equals(ordemServicoResponseDTO.getDataConclusaoOS())){
            throw new IllegalStateException(
                    "A data de conclusão atual da ordem de serviço já é " + dataConclusaoDTO);
        }

        ordemServicoResponseDTO.setDataConclusaoOS(dataConclusaoDTO.getDataConclusaoOS());

        OrdemServico ordemServico = new OrdemServico(ordemServicoResponseDTO);

        return new OrdemServicoResponseDTO(ordemServicoRepository.save(ordemServico));
    }

    public OrdemServicoResponseDTO obterPorId(Integer id) {
        return new OrdemServicoResponseDTO(ordemServicoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("A ordem de serviço com ID " + id + " não foi encontrada.")));
    }

    public OcorrenciaResponseDTO obterOcorrenciaPorId(Integer id){
        return new OcorrenciaResponseDTO(ocorrenciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ocorrência não encontrada pelo ID " + id)));
    }

    public TecnicoResponseDTO obterTecnicoPorCPF(String cpfTecnico){
        return new TecnicoResponseDTO(tecnicoRepository.findByCpf(cpfTecnico)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Técnico não encontrado pelo CPF " + cpfTecnico)));
    }

    /*public List<OrdemServicoResponseDTO> obterLista() {
        return ordemServicoRepository.findAll()
                .stream()
                .map(OrdemServicoResponseDTO::new) // chama o construtor DTO(Tecnico)
                .toList();
    }*/

    public Page<OrdemServicoResponseDTO> obterLista(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        return ordemServicoRepository.findAll(pageable)
                .map(OrdemServicoResponseDTO::new);
    }

    public List<OrdemServicoResponseDTO> listarPorTecnico(String cpf) {
        return ordemServicoRepository.findByTecnicoCpf(cpf)
                .stream()
                .map(OrdemServicoResponseDTO::new) // chama o construtor DTO(Tecnico)
                .toList();
    }

    public List<OrdemServicoResponseDTO> filtrarPorDescricaoServicoEPeriodo(
            String descricaoBusca, LocalDate dataInicio, LocalDate dataFim) {
        return ordemServicoRepository
                .findByDescricaoServicoContainingIgnoreCaseAndDataCriacaoOSBetween(
                        descricaoBusca, dataInicio, dataFim)
                .stream()
                .map(OrdemServicoResponseDTO::new) // chama o construtor DTO(Tecnico)
                .toList();
    }

    public void excluir(Integer id) {
        OrdemServicoResponseDTO ordemServicoResponseDTO = obterPorId(id);
        ordemServicoRepository.delete(new OrdemServico(ordemServicoResponseDTO));
    }

}
