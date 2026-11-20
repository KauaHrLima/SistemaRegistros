package dev.aplication.register.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProdutoRequestDTO(
    @NotBlank(message = "O código do produto nao pode ser vazio")
    @Size(min = 25, max = 50)
    String codigoProduto,

    @NotBlank(message = "O nome do produto nao pode ser vazio")
    @Size(min = 25, max = 150)
    String nomeProduto,

    @NotBlank(message = "Categoria do ID não nula")
    Integer categoriaId,

    @NotBlank(message = "A quantidade do produto nao pode ser vazio")
    @Size(min = 0, max = 255)
    Integer quantidadeProduto
) {
}