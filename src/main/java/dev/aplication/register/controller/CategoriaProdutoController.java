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

import dev.aplication.register.dto.CategoriaProdutoRequestDTO;
import dev.aplication.register.dto.CategoriaProdutoResponseDTO;
import dev.aplication.register.model.CategoriaProduto;
import dev.aplication.register.service.CategoriaProdutoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categoriaProduto")
public class CategoriaProdutoController {

    private final CategoriaProdutoService categoriaProdutoService;

    public CategoriaProdutoController(CategoriaProdutoService categoriaProdutoService) {
        this.categoriaProdutoService = categoriaProdutoService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaProdutoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(categoriaProdutoService.listarTodos().stream()
                .map(CategoriaProdutoResponseDTO::fromEntity)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaProdutoResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(CategoriaProdutoResponseDTO.fromEntity(categoriaProdutoService.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<CategoriaProdutoResponseDTO> salvar(@Valid @RequestBody CategoriaProdutoRequestDTO categoriaProduto) {
        return ResponseEntity.ok(CategoriaProdutoResponseDTO.fromEntity(
                categoriaProdutoService.salvar(toEntity(categoriaProduto))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaProdutoResponseDTO> atualizar(
            @PathVariable Integer id, @Valid @RequestBody CategoriaProdutoRequestDTO dadosAtualizados) {
        return ResponseEntity.ok(CategoriaProdutoResponseDTO.fromEntity(
                categoriaProdutoService.atualizar(id, toEntity(dadosAtualizados))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        categoriaProdutoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private CategoriaProduto toEntity(CategoriaProdutoRequestDTO dados) {
        CategoriaProduto categoria = new CategoriaProduto();
        categoria.setCodigoCategoria(dados.codigoCategoria());
        categoria.setNomeCategoria(dados.nomeCategoria());
        return categoria;
    }
}