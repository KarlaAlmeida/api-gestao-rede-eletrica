package br.edu.infnet.karlaapi.model.repository;

import br.edu.infnet.karlaapi.model.domain.entities.OrdemServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Integer> {

}
