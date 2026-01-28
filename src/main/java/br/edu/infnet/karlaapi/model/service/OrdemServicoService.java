package br.edu.infnet.karlaapi.model.service;

import br.edu.infnet.karlaapi.model.domain.dto.in.OrdemServicoAlteraDataConclusaoDTO;
import br.edu.infnet.karlaapi.model.domain.dto.in.OrdemServicoRequestDTO;
import br.edu.infnet.karlaapi.model.domain.dto.out.OrdemServicoResponseDTO;
import br.edu.infnet.karlaapi.model.domain.entities.Ocorrencia;
import br.edu.infnet.karlaapi.model.domain.entities.OrdemServico;
import br.edu.infnet.karlaapi.model.domain.entities.Tecnico;
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

        Ocorrencia ocorrencia = ocorrenciaRepository.findById(dto.getOcorrenciaId())
                .orElseThrow(() -> new ResourceNotFoundException("Ocorrência não encontrada."));

        Tecnico tecnico = tecnicoRepository.findByCpf(dto.getCpfTecnico())
                .orElseThrow(() -> new ResourceNotFoundException("Técnico não encontrado."));

        OrdemServico ordemServico = new OrdemServico();
        ordemServico.setOcorrencia(ocorrencia);
        ordemServico.setTecnico(tecnico);
        ordemServico.setDataCriacaoOS(LocalDate.now());
        ordemServico.setDescricaoServico(dto.getDescricaoServico());
        ordemServico.setStatusOS(StatusOS.ABERTA);

        ordemServico = ordemServicoRepository.save(ordemServico);

        return new OrdemServicoResponseDTO(ordemServico);
    }

    public OrdemServicoResponseDTO alterar(Integer id, OrdemServicoRequestDTO dto) {

        OrdemServico ordemServico = ordemServicoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("A ordem de serviço com ID " + id + " não foi encontrada.")
                );

        if (dto.getOcorrenciaId() != null) {
            Ocorrencia ocorrencia = ocorrenciaRepository.findById(dto.getOcorrenciaId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Ocorrência não encontrada pelo ID " + dto.getOcorrenciaId())
                    );
            ordemServico.setOcorrencia(ocorrencia);
        }

        if (dto.getCpfTecnico() != null) {
            Tecnico tecnico = tecnicoRepository.findByCpf(dto.getCpfTecnico())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Técnico não encontrada pelo CPF " + dto.getCpfTecnico())
                    );
            ordemServico.setTecnico(tecnico);
        }

        if (dto.getDescricaoServico() != null) {
            ordemServico.setDescricaoServico(dto.getDescricaoServico());
        }

        OrdemServico ordemServicoAtualizada = ordemServicoRepository.save(ordemServico);

        return new OrdemServicoResponseDTO(ordemServicoAtualizada);
    }


    public OrdemServicoResponseDTO alterarStatus(Integer id, StatusOS statusNovo) {

        OrdemServico ordemServico = ordemServicoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("A ordem de serviço com ID " + id + " não foi encontrada.")
                );

        if (statusNovo.equals(ordemServico.getStatusOS())) {
            throw new IllegalStateException(
                    "O status atual da ordem de serviço já é " + statusNovo
            );
        }

        ordemServico.setStatusOS(statusNovo);

        OrdemServico ordemServicoAtualizada = ordemServicoRepository.save(ordemServico);

        return new OrdemServicoResponseDTO(ordemServicoAtualizada);
    }

    public OrdemServicoResponseDTO alterarDataConclusao(
            Integer id,
            OrdemServicoAlteraDataConclusaoDTO dto
    ) {

        OrdemServico ordemServico = ordemServicoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "A ordem de serviço com ID " + id + " não foi encontrada."
                        )
                );

        LocalDate novaData = dto.getDataConclusaoOS();

        if (novaData == null) {
            throw new IllegalArgumentException("Data de conclusão é obrigatória.");
        }

        if (novaData.equals(ordemServico.getDataConclusaoOS())) {
            throw new IllegalStateException(
                    "A data de conclusão atual da ordem de serviço já é " + novaData
            );
        }

        ordemServico.setDataConclusaoOS(novaData);

        OrdemServico ordemServicoAtualizada = ordemServicoRepository.save(ordemServico);

        return new OrdemServicoResponseDTO(ordemServicoAtualizada);
    }


    public OrdemServicoResponseDTO obterPorId(Integer id) {
        OrdemServico ordemServico = ordemServicoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("A ordem de serviço com ID " + id + " não foi encontrada.")
                );
        return new OrdemServicoResponseDTO(ordemServico);
    }


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
        OrdemServico ordemServico = ordemServicoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("A ordem de serviço com ID " + id + " não foi encontrada.")
                );
        ordemServicoRepository.delete(ordemServico);
    }

}
