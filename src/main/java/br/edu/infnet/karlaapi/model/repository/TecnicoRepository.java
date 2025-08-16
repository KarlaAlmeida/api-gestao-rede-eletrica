package br.edu.infnet.karlaapi.model.repository;

import br.edu.infnet.karlaapi.model.domain.entities.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

}
