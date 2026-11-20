package dev.aplication.register.dto;

import java.time.LocalDateTime;

import dev.aplication.register.model.Produto;

public record ProdutoResponseDTO(
    Integer id,
    String codigoProduto,
    String nomeProduto,
    Integer categoriaId,
    Integer quantidadeProduto,
    LocalDateTime cadastradoEm
) {
    public static ProdutoResponseDTO fromEntity(Produto p) {
        return new ProdutoResponseDTO(
            p.getId(),
            p.getCodigoProduto(),
            p.getNomeProduto(),
            p.getCategoriaId(),
            p.getQuantidadeProduto(),
            p.getCadastradoEm()
        );
    }
}