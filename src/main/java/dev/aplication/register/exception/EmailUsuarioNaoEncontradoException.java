package dev.aplication.register.exception;

public class EmailUsuarioNaoEncontradoException extends RuntimeException {

    public EmailUsuarioNaoEncontradoException(String emailUsuario) {
        super("E-maiil de usuário nao foi encontrado: " + emailUsuario);
    }
}