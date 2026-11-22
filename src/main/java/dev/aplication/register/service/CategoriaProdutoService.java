package dev.aplication.register.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.aplication.register.model.CategoriaProduto;
import dev.aplication.register.repository.CategoriaProdutoRepository;

@Service
public class CategoriaProdutoService {

    private final CategoriaProdutoRepository categoriaProdutoRepository;

    public CategoriaProdutoService(CategoriaProdutoRepository categoriaProdutoRepository) {
        this.categoriaProdutoRepository = categoriaProdutoRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoriaProduto> listarTodos() {
        return categoriaProdutoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CategoriaProduto buscarPorId(Integer id) {
        return categoriaProdutoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ID da categoria do produto não encontrado: " + id));
    }

    @Transactional
    public CategoriaProduto salvar(CategoriaProduto categoriaProdutoSalvar) {
        validarCodigoProduto(categoriaProdutoSalvar);

        if (categoriaProdutoSalvar.getId() != null) {
            throw new IllegalArgumentException("Para realizar a alteração da categoria, utilize o método atualizar");
        }

        categoriaProdutoSalvar.setCodigoCategoria(categoriaProdutoSalvar.getCodigoCategoria().trim());
        categoriaProdutoSalvar.setNomeCategoria(categoriaProdutoSalvar.getNomeCategoria().trim());

        if (categoriaProdutoRepository.existsByCodigoCategoria(categoriaProdutoSalvar.getCodigoCategoria())) {
            throw new IllegalArgumentException("Código da categoria do produto ja está cadastrada.");
        }

        if (categoriaProdutoRepository.existsByNomeCategoria(categoriaProdutoSalvar.getNomeCategoria())) {
            throw new IllegalArgumentException("Nome da categoria do produto ja cadastrado");
        }

        return categoriaProdutoRepository.save(categoriaProdutoSalvar);
    }

    @Transactional
    public CategoriaProduto atualizar(Integer id, CategoriaProduto categoriaProdutoAtualizar) {
        if (id == null) {
            throw new IllegalArgumentException("ID da categoria do produto para atualizar não pode ser nulo: " + id);
        }

        validarCodigoProduto(categoriaProdutoAtualizar);

        CategoriaProduto categoriaProdutoExistente = buscarPorId(id);

        String codigoProduto = categoriaProdutoAtualizar.getCodigoCategoria().trim();
        String nomeCategoriaProduto = categoriaProdutoAtualizar.getNomeCategoria().trim();

        categoriaProdutoRepository.findByCodigoCategoria(codigoProduto)
                .filter(atualizado -> !id.equals(categoriaProdutoAtualizar.getId()))
                .ifPresent(atualizado -> {
                    throw new IllegalArgumentException("Código da categoria do produto ja está cadastrado: " + id);
                });

        categoriaProdutoRepository.findByNomeCategoria(nomeCategoriaProduto)
                .filter(atualizado -> !id.equals(atualizado.getId()))
                .ifPresent(atualizado -> {
                    throw new IllegalArgumentException("Nome da categoria do produto ja foi cadastrado no sistema: " + nomeCategoriaProduto);
                });
        
        categoriaProdutoExistente.setCodigoCategoria(codigoProduto);
        categoriaProdutoExistente.setNomeCategoria(nomeCategoriaProduto);

        return categoriaProdutoRepository.save(categoriaProdutoExistente);
    }

    @Transactional
    public void deletar(Integer id) {
        CategoriaProduto categoria = buscarPorId(id);
        categoriaProdutoRepository.delete(categoria);
    }

    private void validarCodigoProduto(CategoriaProduto categoriaProduto) {
        if (categoriaProduto == null) {
            throw new IllegalArgumentException("A categoria do produto nao pode ser nula");
        }

        if (categoriaProduto.getCodigoCategoria() == null || categoriaProduto.getNomeCategoria().trim().isEmpty()) {
            throw new IllegalArgumentException("O código do produto nao pode ser vazio");
        }

        if (categoriaProduto.getNomeCategoria() == null || categoriaProduto.getNomeCategoria().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da categoria do produto nao pode ser nula");
        }
    }
}