package dev.aplication.register.dto;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(
    @NotBlank(message = "O nome de usuário nao pode ser vazio")
    @Size(min = 3, max = 120, message = "O nome de usuário deve conter no máximo 120 caracteres")
    String nomeUsuario,

    @NotBlank(message = "E-mail do usuário nao pode ser vazio")
    @Email(message = "Formato de e-mail inválido para cadastro")
    @Size(max = 120)
    String emailUsuario,

    @NotBlank(message = "Telefone de usuário nao pode ser vazio")
    @Size(max = 20)
    String telefoneUsuario,

    @NotBlank(message = "CPF do usuário nao pode ser vazio")
    @CPF(message = "Formato de CPF inválido para cadastro")
    @Size(max = 20)
    String cpfUsuario,

    @NotBlank(message = "RG do usuário nao pode ser vazio")
    @Size(max = 20)
    String rgUsuario
) {
}