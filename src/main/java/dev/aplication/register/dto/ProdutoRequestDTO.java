package dev.aplication.register.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ProdutoRequestDTO(
    @NotBlank(message = "O código do produto nao pode ser vazio")
    @Size(max = 50)
    String codigoProduto,

    @NotBlank(message = "O nome do produto nao pode ser vazio")
    @Size(max = 150)
    String nomeProduto,

    @NotNull(message = "A categoria do produto é obrigatória")
    @Positive(message = "O ID da categoria deve ser positivo")
    Integer categoriaId,

    @NotNull(message = "A quantidade do produto é obrigatória")
    @PositiveOrZero(message = "A quantidade do produto não pode ser negativa")
    Integer quantidadeProduto
) {
}