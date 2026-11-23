package dev.aplication.register.exception;

public class CpfUsuarioNaoEncontradoException extends RuntimeException {

    public CpfUsuarioNaoEncontradoException(String cpfUsuario) {
        super("CPF de usuário nao pode ser encontrado: " + cpfUsuario);
    }
}