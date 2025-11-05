package com.EngCode.BFF_Agendador_de_Tarefas.controller;

// BLOCÃO 1: IMPORTAÇÕES ESSENCIAIS
// -------------------------------------------------------------------------

// Importa suas Exceções Personalizadas (ResourceNotFound, Conflict, Unauthorized).


import com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.exceptions.ConflictException;
import com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.exceptions.IllegalArgumentException;
import com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.exceptions.ResourceNotFoundException;
import com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// BLOCÃO 2: ESTRUTURA GERAL
// -------------------------------------------------------------------------

@ControllerAdvice
// ANOTAÇÃO PRINCIPAL: Habilita esta classe a "ouvir" e interceptar exceções
// lançadas em QUALQUER @Controller da sua aplicação. Isso garante o tratamento de erros GLOBAL.
public class GlobalExceptionHandler {

    // BLOCÃO 3: TRATAMENTO DE ResourceNotFoundException (HTTP 404 - Não Encontrado)
    // -------------------------------------------------------------------------

    @ExceptionHandler(ResourceNotFoundException.class)
    // Mapeia: Diz ao Spring para executar este método sempre que uma ResourceNotFoundException for lançada (geralmente pelo Service).
    public ResponseEntity<String> handlerResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        // FUNÇÃO: Captura a exceção, pega a mensagem de erro que foi definida nela.

        // Retorna um novo ResponseEntity:
        // 1. Corpo: A mensagem de erro (resourceNotFoundException.getMessage()).
        // 2. Status: HttpStatus.NOT_FOUND (Código 404).
        return new ResponseEntity<>(resourceNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    // BLOCÃO 4: TRATAMENTO DE ConflictException (HTTP 409 - Conflito)
    // -------------------------------------------------------------------------

    @ExceptionHandler(ConflictException.class)
    // Mapeia: Diz ao Spring para executar este método quando uma ConflictException for lançada (ex: tentar cadastrar e-mail duplicado).
    public ResponseEntity<String> handlerConflictException(ConflictException conflictException) {
        // FUNÇÃO: Captura a exceção de Conflito.

        // Retorna: A mensagem da exceção com o Status HTTP 409 (CONFLICT).
        return new ResponseEntity<>(conflictException.getMessage(), HttpStatus.CONFLICT);
    }

    // BLOCÃO 5: TRATAMENTO DE UnauthorizedException (HTTP 401 - Não Autorizado)
    // -------------------------------------------------------------------------

    @ExceptionHandler(UnauthorizedException.class)
    // Mapeia: Diz ao Spring para executar este método quando uma UnauthorizedException for lançada (geralmente relacionada a falha de JWT/permissão).
    public ResponseEntity<String> handlerUnauthorizedException(UnauthorizedException unauthorizedException) {
        // FUNÇÃO: Captura a exceção de Não Autorizado.

        // Retorna: A mensagem da exceção com o Status HTTP 401 (UNAUTHORIZED).
        return new ResponseEntity<>(unauthorizedException.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    // BLOCÃO 5: TRATAMENTO DE UnauthorizedException (HTTP 401 - Não Autorizado)
    // -------------------------------------------------------------------------

    @ExceptionHandler(IllegalArgumentException.class)
    // Mapeia: Diz ao Spring para executar este método quando uma UnauthorizedException for lançada (geralmente relacionada a falha de JWT/permissão).
    public ResponseEntity<String> handlerIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
        // FUNÇÃO: Captura a exceção de Não Autorizado.

        // Retorna: A mensagem da exceção com o Status HTTP 401 (UNAUTHORIZED).
        return new ResponseEntity<>(illegalArgumentException.getMessage(), HttpStatus.BAD_REQUEST);
    }

}