package com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.exceptions;
// Define o pacote onde a exceção reside.


// Importa a classe pai do Spring Security, que é a base para exceções relacionadas à autenticação.

// BLOCÃO 1: DEFINIÇÃO DA CLASSE
// -------------------------------------------------------------------------
public class BusinessException extends RuntimeException {
// A classe estende AuthenticationException. Isso a torna uma exceção tratável pelo
// Spring Security, que irá automaticamente mapeá-la para o fluxo de erro de segurança
// (geralmente resultando em um HTTP 401 Unauthorized).

    // BLOCÃO 2: CONSTRUTORES (Formas de Lançar a Exceção)
    // -------------------------------------------------------------------------

    public BusinessException(String mensagem) {
        // Construtor Básico: Usado para lançar a exceção com uma mensagem de erro simples.
        // Exemplo de uso: new UnauthorizedException("Token JWT inválido ou expirado.");
        super(mensagem);
        // Chama o construtor da classe pai (AuthenticationException) para armazenar a mensagem.
    }

    public BusinessException(String mensagem, Throwable throwable) {
        // Construtor Completo: Usado para "embrulhar" uma exceção de baixo nível (a 'causa').
        super(mensagem, throwable);
        // FUNÇÃO: Permite que você lance sua exceção de segurança, mas preserve a exceção
        // original (ex: do cliente Feign ou do Java Security) para melhor rastreabilidade (debugging).
    }

}