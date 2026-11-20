package dev.aplication.register.dto;

import java.time.LocalDateTime;

import dev.aplication.register.model.Usuario;

public record UsuarioResponseDTO(
    Integer id,
    String nomeUsuario,
    String emailUsuario,
    String telefoneUsuario,
    String cpfUsuario,
    String rgUsuario,
    LocalDateTime criadoEm,
    LocalDateTime atualizadoEm
) {
    public static UsuarioResponseDTO fromEntity(Usuario u) {
        return new UsuarioResponseDTO(
            u.getId(),
            u.getNomeUsuario(),
            u.getEmailUsuario(),
            u.getTelefoneUsuario(),
            u.getCpfUsuario(),
            u.getRgUsuario(),
            u.getCriadoEm(),
            u.getAtualizadoEm()
        );
    }
}