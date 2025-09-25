package br.edu.infnet.karlaapi.model.repository;

import br.edu.infnet.karlaapi.model.domain.entities.Ocorrencia;
import br.edu.infnet.karlaapi.model.domain.entities.OrdemServico;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Integer> {

    @Override
    @EntityGraph(attributePaths =
            {"ocorrencia", "ocorrencia.ativo", "ocorrencia.ativo.endereco", "tecnico", "tecnico.endereco"})
    List<OrdemServico> findAll();

    @Override
    @EntityGraph(attributePaths = {"ocorrencia", "ocorrencia.ativo", "ocorrencia.ativo.endereco", "tecnico", "tecnico.endereco"})
    Optional<OrdemServico> findById(Integer id);


    List<OrdemServico> findByTecnicoCpf(String cpf);

    List<OrdemServico> findByDescricaoServicoContainingIgnoreCaseAndDataCriacaoOSBetween(
            String descricaoBusca, LocalDate dataInicio, LocalDate dataFim);

}
