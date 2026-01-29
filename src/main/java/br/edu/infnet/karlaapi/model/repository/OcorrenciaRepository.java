package br.edu.infnet.karlaapi.model.repository;

import br.edu.infnet.karlaapi.model.domain.entities.Ocorrencia;
import br.edu.infnet.karlaapi.model.infraestructure.enums.StatusOcorrencia;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Integer> {

    @Override
    @EntityGraph(attributePaths = {"ordensServico", "ativo", "ativo.endereco"})
    List<Ocorrencia> findAll();

    @Override
    @EntityGraph(attributePaths = {"ordensServico", "ativo", "ativo.endereco"})
    Optional<Ocorrencia> findById(Integer id);

    long count();

    long countByStatusOcorrencia(StatusOcorrencia status);

    @Query("""
            select a.tipoAtivo, count(o)
            from Ocorrencia o
            join o.ativo a
            group by a.tipoAtivo
            """)
    List<Object[]> countOcorrenciasPorTipoAtivo();
}
