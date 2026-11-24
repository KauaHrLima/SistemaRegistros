package dev.aplication.register.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaProdutoRequestDTO(
    @NotBlank(message = "O codigo de categoria do produto é obrigatório")
    @Size(max = 20, message = "O código de categoria do produto deve conter no máximo 20 caracteres")
    String codigoCategoria,

    @NotBlank(message = "o nome da categoria do produto nao pode ser vazia")
    @Size(max = 100, message = "O nome da categoria do produto deve ter no máximo 100 caracteres")
    String nomeCategoria
) {
}