package dev.aplication.register.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.aplication.register.model.Usuario;

public interface UsuarioRespository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmailUsuario(String emailUsuario);

    Optional<Usuario> findByCpfUsuario(String cpfUsuario);

    Optional<Usuario> findByRgUsuario(String rgUsuario);

    boolean existsByEmailUsuario(String emailUsuario);

    boolean existsByCpfUsuario(String cpfUsuario);
}