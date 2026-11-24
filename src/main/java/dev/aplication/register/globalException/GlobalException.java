package dev.aplication.register.globalException;

import java.util.Map;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.aplication.register.exception.CodigoProdutoNaoEncontradoException;
import dev.aplication.register.exception.CpfUsuarioNaoEncontradoException;
import dev.aplication.register.exception.EmailUsuarioNaoEncontradoException;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler({
            EntityNotFoundException.class,
            CodigoProdutoNaoEncontradoException.class,
            CpfUsuarioNaoEncontradoException.class,
            EmailUsuarioNaoEncontradoException.class
    })
    public ResponseEntity<Map<String, String>> handleNaoEncontrado(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("mensagem", exception.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleArgumentoInvalido(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(Map.of("mensagem", exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidacao(MethodArgumentNotValidException exception) {
        String mensagem = exception.getBindingResult().getFieldErrors().stream()
                .map(erro -> erro.getDefaultMessage())
                .findFirst()
                .orElse("Dados inválidos.");

        return ResponseEntity.badRequest().body(Map.of("mensagem", mensagem));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleErroInterno(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("mensagem", "Ocorreu um erro interno no servidor."));
    }
}