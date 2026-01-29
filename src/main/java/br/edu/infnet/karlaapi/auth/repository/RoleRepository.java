package br.edu.infnet.karlaapi.auth.repository;

import br.edu.infnet.karlaapi.auth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNome(String nome);
}
