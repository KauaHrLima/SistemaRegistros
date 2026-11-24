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

import dev.aplication.register.dto.ProdutoRequestDTO;
import dev.aplication.register.dto.ProdutoResponseDTO;
import dev.aplication.register.model.Produto;
import dev.aplication.register.service.ProdutoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(produtoService.listarTodos().stream()
                .map(ProdutoResponseDTO::fromEntity)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ProdutoResponseDTO.fromEntity(produtoService.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> salvar(@Valid @RequestBody ProdutoRequestDTO produtoSalvo) {
        return ResponseEntity.ok(ProdutoResponseDTO.fromEntity(produtoService.salvar(toEntity(produtoSalvo))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(
            @PathVariable Integer id, @Valid @RequestBody ProdutoRequestDTO dadosAtualizados) {
        return ResponseEntity.ok(ProdutoResponseDTO.fromEntity(
                produtoService.atualizar(id, toEntity(dadosAtualizados))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private Produto toEntity(ProdutoRequestDTO dados) {
        Produto produto = new Produto();
        produto.setCodigoProduto(dados.codigoProduto());
        produto.setNomeProduto(dados.nomeProduto());
        produto.setCategoriaId(dados.categoriaId());
        produto.setQuantidadeProduto(dados.quantidadeProduto());
        return produto;
    }
}