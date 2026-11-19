package dev.aplication.register.model;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "codigo_produto", nullable = false, length = 50, unique = true)
    private String codigoProduto;

    @Column(name = "nome_produto", nullable = false, length = 150)
    private String nomeProduto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categoria_id")
    @Column(name = "categoria_id", nullable = false)
    private Integer categoriaId;

    @Column(name = "quantidade_produto", nullable = false)
    private Integer quantidadeProduto;
}