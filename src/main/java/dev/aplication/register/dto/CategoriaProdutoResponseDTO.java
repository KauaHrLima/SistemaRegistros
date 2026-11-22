package dev.aplication.register.dto;

import dev.aplication.register.model.CategoriaProduto;

public record CategoriaProdutoResponseDTO(
    Integer id,
    String codigoCategoria,
    String nomeCategoria
) {
    public static CategoriaProdutoResponseDTO fromEntity(CategoriaProduto cp) {
        return new CategoriaProdutoResponseDTO(
            cp.getId(),
            cp.getCodigoCategoria(),
            cp.getNomeCategoria()
        );
    }
}