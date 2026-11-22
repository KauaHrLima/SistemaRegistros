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

    @Transactional(readOnly = true) // readOnly = true -> apenas para leituras de dados listados
    public List<Usuario> listarTodos() {
        return usuarioRespository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Integer id) {
        return usuarioRespository.findById(id)
                .orElseThrow(() -> new RuntimeException("ID do usuário nao encontrado: " + id));
    }

    @Transactional // @Transactional -> abre uma transição no banco de dados antes de rodar o método
    public Usuario salvar(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario nao pode ser nulo");
        }

        if (campoVazio(usuario.getNomeUsuario())
                || campoVazio(usuario.getEmailUsuario())
                || campoVazio(usuario.getTelefoneUsuario())
                || campoVazio(usuario.getCpfUsuario())
                || campoVazio(usuario.getRgUsuario())) {
            throw new IllegalArgumentException("Os campos nao podem ser vazios");
        }

        usuario.setNomeUsuario(usuario.getNomeUsuario().trim());
        usuario.setEmailUsuario(usuario.getEmailUsuario().trim());
        usuario.setTelefoneUsuario(usuario.getTelefoneUsuario().trim());
        usuario.setCpfUsuario(usuario.getCpfUsuario().trim());
        usuario.setRgUsuario(usuario.getRgUsuario().trim());

        usuarioRespository.findByEmailUsuario(usuario.getEmailUsuario())
                .filter(existente -> usuario.getId() == null || !existente.getId().equals(usuario.getId()))
                .ifPresent(existente -> {
                    throw new IllegalArgumentException("E-mail já cadastrado: " + usuario.getEmailUsuario());
                });

        return usuarioRespository.save(usuario);
    }

    @Transactional
    public Usuario atualizar(Integer id, Usuario dadosAtualizados) {
        if (id == null) {
            throw new IllegalArgumentException("O ID do usuário nao pode ser nulo");
        }

        if (campoVazio(dadosAtualizados.getNomeUsuario())
                || campoVazio(dadosAtualizados.getEmailUsuario())
                || campoVazio(dadosAtualizados.getTelefoneUsuario())
                || campoVazio(dadosAtualizados.getCpfUsuario())
                || campoVazio(dadosAtualizados.getRgUsuario())) {
            throw new IllegalArgumentException("Os campos para atualizar os dados do usuário nao pode ser vazio");
        }

        Usuario usuarioExistente = buscarPorId(id);

        String emailUsuario = dadosAtualizados.getEmailUsuario().trim();
        String cpfUsuario = dadosAtualizados.getCpfUsuario().trim();

        usuarioRespository.findByEmailUsuario(emailUsuario)
                .filter(atualizado -> !id.equals(atualizado.getId()))
                .filter(atualizado -> {
                    throw new IllegalArgumentException("CPF de usuário ja cadastrado no sistema: " + cpfUsuario);
                });

        usuarioExistente.setNomeUsuario(dadosAtualizados.getNomeUsuario().trim());
        usuarioExistente.setEmailUsuario(dadosAtualizados.getEmailUsuario().trim());
        usuarioExistente.setTelefoneUsuario(dadosAtualizados.getTelefoneUsuario().trim());
        usuarioExistente.setCpfUsuario(dadosAtualizados.getCpfUsuario().trim());
        usuarioExistente.setRgUsuario(dadosAtualizados.getRgUsuario().trim());

        return usuarioRespository.save(usuarioExistente);
    }

    @Transactional
    public void deletar(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do usuário nao pode ser nulo para deletar.");
        }

        Usuario usuario = buscarPorId(id);
        usuarioRespository.delete(usuario);
    }

    // Verificador de campos vazios
    // declaração privada de valor lógico
    private boolean campoVazio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}