package dev.aplication.register.exception;

public class CodigoProdutoNaoEncontradoException extends RuntimeException {
    public CodigoProdutoNaoEncontradoException(String codigoCategoria) {
        super("Codigo da categoria nao pode ser encontrado: " + codigoCategoria);
    }
}