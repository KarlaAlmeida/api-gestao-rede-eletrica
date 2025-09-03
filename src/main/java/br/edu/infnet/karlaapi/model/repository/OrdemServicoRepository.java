package br.edu.infnet.karlaapi.model.repository;

import br.edu.infnet.karlaapi.model.domain.entities.OrdemServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Integer> {

    List<OrdemServico> findByTecnicoCpf(String cpf);

    List<OrdemServico> findByDescricaoServicoContainingIgnoreCaseAndDataCriacaoOSBetween(
            String descricaoBusca, LocalDate dataInicio, LocalDate dataFim);
}
