package dev.aplication.register.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.aplication.register.model.CategoriaProduto;

public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Integer> {

    Optional<CategoriaProduto> findByCodigoCategoria (String codigoCategori);

    Optional<CategoriaProduto> findByNomeCategoria (String nomeCategoria);

    boolean existsByCodigoCategoria(String codigoCategoria);

    boolean existsByNomeCategoria(String nomeCategoria);
}