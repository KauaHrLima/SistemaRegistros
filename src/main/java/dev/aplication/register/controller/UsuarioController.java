package dev.aplication.register.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.aplication.register.dto.UsuarioRequestDTO;
import dev.aplication.register.dto.UsuarioResponseDTO;
import dev.aplication.register.model.Usuario;
import dev.aplication.register.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos().stream()
                .map(UsuarioResponseDTO::fromEntity)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(UsuarioResponseDTO.fromEntity(usuarioService.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> salvar(@Valid @RequestBody UsuarioRequestDTO usuario) {
        return ResponseEntity.ok(UsuarioResponseDTO.fromEntity(usuarioService.salvar(toEntity(usuario))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(
            @PathVariable Integer id, @Valid @RequestBody UsuarioRequestDTO dadosAtualizados) {
        return ResponseEntity.ok(UsuarioResponseDTO.fromEntity(
                usuarioService.atualizar(id, toEntity(dadosAtualizados))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private Usuario toEntity(UsuarioRequestDTO dados) {
        Usuario usuario = new Usuario();
        usuario.setNomeUsuario(dados.nomeUsuario());
        usuario.setEmailUsuario(dados.emailUsuario());
        usuario.setTelefoneUsuario(dados.telefoneUsuario());
        usuario.setCpfUsuario(dados.cpfUsuario());
        usuario.setRgUsuario(dados.rgUsuario());
        return usuario;
    }
}