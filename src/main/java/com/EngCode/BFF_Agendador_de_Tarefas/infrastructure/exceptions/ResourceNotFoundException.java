package com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.exceptions;
// Define o pacote onde esta exceção de infraestrutura reside.

// BLOCÃO 1: DEFINIÇÃO DA CLASSE
// -------------------------------------------------------------------------

public class ResourceNotFoundException extends RuntimeException {
// A classe estende RuntimeException (Exceção Não Verificada/unchecked).
// FUNÇÃO: Sinaliza que a aplicação não encontrou o que estava procurando (ex: ID ou e-mail).
// CONCEITO: O Spring Boot, através de um Handler (que você deve ter configurado),
// mapeia esta exceção para o Status HTTP 404 (Not Found), informando o cliente
// de forma clara sobre o erro.

    // BLOCÃO 2: CONSTRUTORES
    // -------------------------------------------------------------------------

    public ResourceNotFoundException (String mensagem) {
        // Construtor Básico: Usado para lançar a exceção com uma mensagem descritiva.
        // Exemplo de uso: .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
        super(mensagem);
        // Chama o construtor da classe pai (RuntimeException) para armazenar a mensagem.
    }

    public ResourceNotFoundException (String mensagem, Throwable throwable) {
        // Construtor Completo: Usado para encapsular uma exceção original (a 'causa').
        // CONCEITO: Permite que você lance uma exceção de negócio (ResourceNotFound)
        // mantendo o rastreamento do erro original de baixo nível (se houver).
        super(mensagem, throwable);
    }

}