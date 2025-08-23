package br.edu.infnet.karlaapi.model.repository;

import br.edu.infnet.karlaapi.model.domain.entities.Ocorrencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Integer> {

}
