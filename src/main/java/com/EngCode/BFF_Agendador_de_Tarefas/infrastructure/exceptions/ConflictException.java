package com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.exceptions;
// Define o pacote para as classes de exceção.

// BLOCÃO 1: DEFINIÇÃO DA CLASSE
// -------------------------------------------------------------------------

public class ConflictException extends RuntimeException {
// A classe estende RuntimeException. Isso a torna uma "exceção não verificada" (unchecked exception)
// no Java, o que significa que o compilador não obriga você a envolvê-la em blocos try-catch.
// O Spring Boot, por padrão, mapeia essas exceções para o Status HTTP 500 (Internal Server Error),
// mas você deve ter um Handler (Controlador de Exceções) para mapeá-la para o Status 409 (Conflict).

// BLOCÃO 2: CONSTRUTORES (Formas de Criar a Exceção)
// -------------------------------------------------------------------------

    public ConflictException (String mensagem) {
        // Construtor Básico: Recebe apenas a mensagem de erro (ex: "E-mail já cadastrado.").
        super(mensagem);
        // Chama o construtor da classe pai (RuntimeException) para armazenar a mensagem.
    }

    public ConflictException (String mensagem, Throwable throwable) {
        // Construtor Completo: Recebe a mensagem e a 'causa' original da exceção (Throwable).
        super(mensagem, throwable);
        // Esse construtor é útil para encapsular (embrulhar) uma exceção de baixo nível
        // (ex: do banco de dados) em uma exceção de negócio (ConflictException),
        // mantendo a rastreabilidade do erro original (stack trace).
    }

}