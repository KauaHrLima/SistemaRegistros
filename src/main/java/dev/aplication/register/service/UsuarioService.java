package dev.aplication.register.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.aplication.register.model.Usuario;
import dev.aplication.register.repository.UsuarioRespository;

@Service
public class UsuarioService {

    private final UsuarioRespository usuarioRespository;

    public UsuarioService(UsuarioRespository usuarioRespository) {
        this.usuarioRespository = usuarioRespository;
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return usuarioRespository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Integer id) {
        return usuarioRespository.findById(id)
            .orElseThrow(() -> new RuntimeException("ID do usuário nao encontrado: " + id));
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        return null;
    }
}