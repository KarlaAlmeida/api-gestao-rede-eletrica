package br.edu.infnet.karlaapi.model.service;

import br.edu.infnet.karlaapi.model.domain.dto.DashboardResponseDTO;
import br.edu.infnet.karlaapi.model.infraestructure.enums.StatusOS;
import br.edu.infnet.karlaapi.model.infraestructure.enums.StatusOcorrencia;
import br.edu.infnet.karlaapi.model.repository.AtivoRepository;
import br.edu.infnet.karlaapi.model.repository.OcorrenciaRepository;
import br.edu.infnet.karlaapi.model.repository.OrdemServicoRepository;
import br.edu.infnet.karlaapi.model.repository.TecnicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {


    private final AtivoRepository ativoRepository;
    private final TecnicoRepository tecnicoRepository;
    private final OcorrenciaRepository ocorrenciaRepository;
    private final OrdemServicoRepository ordemServicoRepository;


    public DashboardResponseDTO obterDashboard() {


        Map<String, Long> ocorrenciasPorTipoAtivo = ocorrenciaRepository
                .countOcorrenciasPorTipoAtivo()
                .stream()
                .collect(Collectors.toMap(
                        o -> o[0].toString(),
                        o -> (Long) o[1]
                ));


        Map<String, Long> ordensPorTecnico = ordemServicoRepository
                .countOrdensPorTecnico()
                .stream()
                .collect(Collectors.toMap(
                        o -> o[0].toString(),
                        o -> (Long) o[1]
                ));


        return new DashboardResponseDTO(
                ativoRepository.count(),
                tecnicoRepository.count(),
                ocorrenciaRepository.count(),
                ocorrenciaRepository.countByStatusOcorrencia(StatusOcorrencia.CONCLUIDA),
                ordemServicoRepository.count(),
                ordemServicoRepository.countByStatusOS(StatusOS.CONCLUIDA),
                ocorrenciasPorTipoAtivo,
                ordensPorTecnico
        );
    }
}