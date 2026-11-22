package dev.aplication.register.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.aplication.register.model.Produto;
import dev.aplication.register.model.Usuario;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    Optional<Usuario> findByCodigoProduto(String codigoProduto);

    Optional<Usuario> findByNomeProduto(String nomeProduto);

    boolean existsByCodigoProduto(String codigoProduto);

    boolean existsByNomeProduto(String nomeProduto);
}