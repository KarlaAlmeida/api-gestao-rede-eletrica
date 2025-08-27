package br.edu.infnet.karlaapi.model.repository;

import br.edu.infnet.karlaapi.model.domain.entities.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

    Optional<Tecnico> findByCpf(String cpf);

    List<Tecnico> findByNomeStartingWithIgnoreCaseAndEspecialidadeIgnoreCase(
            String prefixoNome, String especialidade);
}
