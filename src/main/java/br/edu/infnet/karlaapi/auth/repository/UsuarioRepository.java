package br.edu.infnet.karlaapi.auth.repository;

import br.edu.infnet.karlaapi.auth.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}
