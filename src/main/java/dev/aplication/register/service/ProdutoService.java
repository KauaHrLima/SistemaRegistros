package dev.aplication.register.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.aplication.register.model.Produto;
import dev.aplication.register.repository.ProdutoRepository;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional(readOnly = true)
    public List<Produto> listarTodos() {
        @SuppressWarnings("unchecked")
        List<Produto> produtos = (List<Produto>) (List<?>) produtoRepository.findAll();
        return produtos;
    }

    @Transactional(readOnly = true)
    public Produto buscarPorId(Integer id) {
        Object produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ID do produto nao encontrado: " + id));
        return (Produto) produto;
    }

    @Transactional
    public Produto salvar(Produto produtoSalvar) {
        validarProduto(produtoSalvar);

        if (produtoSalvar.getId() != null) {
            throw new IllegalArgumentException("Para mudar o produtoSalvar, use o método atualizar.");
        }

        String codigo = produtoSalvar.getCodigoProduto().trim();

        if (produtoRepository.existsByCodigoProduto(codigo)) {
            throw new IllegalArgumentException("Código do produtoSalvar ja está cadastrado: " + codigo);
        }

        produtoSalvar.setCodigoProduto(codigo);
        produtoSalvar.setNomeProduto(produtoSalvar.getNomeProduto().trim());
        produtoSalvar.setCategoriaId(produtoSalvar.getCategoriaId());
        produtoSalvar.setCadastradoEm(LocalDateTime.now());

        return produtoRepository.save(produtoSalvar);
    }

    @Transactional
    public Produto atualizar(Integer id, Produto produtoAtualizado) {
        if (id == null) {
            throw new IllegalArgumentException("ID do produto nao foi encontrado: " + id);
        }

        validarProduto(produtoAtualizado);

        Produto produtoExistente = buscarPorId(id);
        String codigo = produtoAtualizado.getCodigoProduto().trim();

        produtoRepository.findByCodigoProduto(codigo)
                .filter(atualizado -> !id.equals(atualizado.getId()))
                .ifPresent(atualizado -> {
                    throw new IllegalArgumentException(
                            "Codigo do produto informado ja está cadastrado no sistema: " + codigo);
                });

        produtoExistente.setCodigoProduto(codigo);
        produtoExistente.setNomeProduto(produtoAtualizado.getNomeProduto().trim());
        produtoExistente.setCategoriaId(produtoAtualizado.getCategoriaId());
        produtoExistente.setQuantidadeProduto(produtoAtualizado.getQuantidadeProduto());

        return produtoRepository.save(produtoExistente);
    }

    @Transactional
    public void deletar(Integer id) {
        Produto produto = buscarPorId(id);
        produtoRepository.delete(produto);
    }

    private void validarProduto(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo");
        }

        if (produto.getCodigoProduto() == null || produto.getCodigoProduto().trim().isEmpty()) {
            throw new IllegalArgumentException("Código do produto não pode ser vazio");
        }

        if (produto.getNomeProduto() == null || produto.getNomeProduto().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto não pode ser vazio");
        }

        if (produto.getCategoriaId() == null) {
            throw new IllegalArgumentException("Categoria do produto é obrigatória");
        }

        if (produto.getQuantidadeProduto() == null || produto.getQuantidadeProduto() < 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior ou igual a zero");
        }
    }
}