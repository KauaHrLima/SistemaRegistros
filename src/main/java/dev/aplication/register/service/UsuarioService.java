package dev.aplication.register.service;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.EntityNotFoundException;
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
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + id));
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

        validarDuplicidade(usuario, usuario.getId());

        return usuarioRespository.save(usuario);
    }

    @Transactional
    public Usuario atualizar(Integer id, Usuario dadosAtualizados) {
        if (id == null) {
            throw new IllegalArgumentException("O ID do usuário nao pode ser nulo");
        }

        if (dadosAtualizados == null) {
            throw new IllegalArgumentException("Os dados do usuário não podem ser nulos");
        }

        if (campoVazio(dadosAtualizados.getNomeUsuario())
                || campoVazio(dadosAtualizados.getEmailUsuario())
                || campoVazio(dadosAtualizados.getTelefoneUsuario())
                || campoVazio(dadosAtualizados.getCpfUsuario())
                || campoVazio(dadosAtualizados.getRgUsuario())) {
            throw new IllegalArgumentException("Os campos para atualizar os dados do usuário nao pode ser vazio");
        }

        Usuario usuarioExistente = buscarPorId(id);

        dadosAtualizados.setNomeUsuario(dadosAtualizados.getNomeUsuario().trim());
        dadosAtualizados.setEmailUsuario(dadosAtualizados.getEmailUsuario().trim());
        dadosAtualizados.setTelefoneUsuario(dadosAtualizados.getTelefoneUsuario().trim());
        dadosAtualizados.setCpfUsuario(dadosAtualizados.getCpfUsuario().trim());
        dadosAtualizados.setRgUsuario(dadosAtualizados.getRgUsuario().trim());
        validarDuplicidade(dadosAtualizados, id);

        usuarioExistente.setNomeUsuario(dadosAtualizados.getNomeUsuario());
        usuarioExistente.setEmailUsuario(dadosAtualizados.getEmailUsuario());
        usuarioExistente.setTelefoneUsuario(dadosAtualizados.getTelefoneUsuario());
        usuarioExistente.setCpfUsuario(dadosAtualizados.getCpfUsuario());
        usuarioExistente.setRgUsuario(dadosAtualizados.getRgUsuario());

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

    private void validarDuplicidade(Usuario usuario, Integer idAtual) {
        usuarioRespository.findByEmailUsuario(usuario.getEmailUsuario())
                .filter(existente -> !Objects.equals(idAtual, existente.getId()))
                .ifPresent(existente -> {
                    throw new IllegalArgumentException("E-mail já cadastrado: " + usuario.getEmailUsuario());
                });

        usuarioRespository.findByCpfUsuario(usuario.getCpfUsuario())
                .filter(existente -> !Objects.equals(idAtual, existente.getId()))
                .ifPresent(existente -> {
                    throw new IllegalArgumentException("CPF já cadastrado: " + usuario.getCpfUsuario());
                });

        usuarioRespository.findByRgUsuario(usuario.getRgUsuario())
                .filter(existente -> !Objects.equals(idAtual, existente.getId()))
                .ifPresent(existente -> {
                    throw new IllegalArgumentException("RG já cadastrado: " + usuario.getRgUsuario());
                });
    }
}