package dev.aplication.register.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.aplication.register.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    Optional<Produto> findByCodigoProduto(String codigoProduto);

    Optional<Produto> findByNomeProduto(String nomeProduto);

    boolean existsByCodigoProduto(String codigoProduto);

    boolean existsByNomeProduto(String nomeProduto);
}