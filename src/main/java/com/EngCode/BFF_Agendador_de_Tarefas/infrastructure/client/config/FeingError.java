package com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.client.config;

// BLOCÃO 1: IMPORTAÇÕES E EXCEÇÕES NECESSÁRIAS
// -------------------------------------------------------------------------

// Importa todas as exceções personalizadas que o Feign traduzirá.
import com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.exceptions.BusinessException;
import com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.exceptions.ConflictException;
import com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.exceptions.IllegalArgumentException;
import com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.exceptions.ResourceNotFoundException;
import com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.exceptions.UnauthorizedException;

import feign.Response; // Objeto de resposta HTTP vindo do Microsserviço
import feign.codec.ErrorDecoder; // Interface que obriga a implementação do método decode.

import java.io.IOException; // Necessário para tratar erros de I/O (leitura do corpo da resposta).
import java.nio.charset.StandardCharsets;
import java.util.Objects; // Usado para verificação de objetos (ex: se é nulo).

public class FeingError implements ErrorDecoder {
// IMPLEMENTAÇÃO: Esta classe implementa ErrorDecoder para personalizar como o Feign trata erros HTTP.

    @Override
    // MÉTODO OBRIGATÓRIO: Chamado pelo Feign sempre que recebe um código HTTP de erro (4xx ou 5xx).
    // String s: O nome do método Feign que falhou (útil para logs).
    // Response response: O objeto de resposta HTTP, contendo o status e o corpo do erro.
    public Exception decode(String s, Response response) {


        // BLOCÃO 2: EXTRAÇÃO DA MENSAGEM DO CORPO (Lógica Auxiliar)
        // -------------------------------------------------------------------------
        // Chama o método auxiliar para tentar ler a mensagem detalhada do corpo da resposta HTTP.
        String mensagemErro = mensagemErro(response);


        switch (response.status()) {

            // BLOCÃO 3: MAPEAMENTO DE STATUS HTTP PARA EXCEÇÕES JAVA
            // -------------------------------------------------------------------------

            case 400: // Bad Request (Requisição Malfeita)
                // O BFF traduz o HTTP 400 (dados inválidos/má formação) para a exceção de Argumento Inválido.
                return new IllegalArgumentException(
                        "Erro: " + mensagemErro);

            case 401: // Unauthorized (Não Autorizado)
                // Traduz o HTTP 401 (falha de autenticação/Token) para UnauthorizedException.
                return new UnauthorizedException(
                        "Erro: " + mensagemErro);

            case 403: // Forbidden (Acesso Proibido)
                // Traduz o HTTP 403 (falha de autorização/permissão) para UnauthorizedException.
                return new ResourceNotFoundException(
                        "Erro: " + mensagemErro);

            case 404: // Not Found (Recurso não Encontrado)
                // Traduz o HTTP 404 (recurso inexistente) para ResourceNotFoundException.
                return new ResourceNotFoundException(
                        "Erro: " + mensagemErro);

            case 409: // Conflict (Conflito)
                // Traduz o HTTP 409 (conflito de dados, ex: e-mail duplicado) para ConflictException.
                return new ConflictException(
                        "Erro: " + mensagemErro);

            case 500: // Internal Server Error
                // Traduz o HTTP 500 (falha interna não prevista no Microsserviço de Tarefas) para BusinessException.
                return new BusinessException(
                        "Erro: " + mensagemErro);

            default:
                // Retorno Padrão: Para qualquer outro código (ex: 502, 503, etc.), lança BusinessException.
                return new BusinessException("Erro: " + mensagemErro);
        }

    }

    // BLOCÃO 4: MÉTODO AUXILIAR PARA EXTRAÇÃO DA MENSAGEM
    // -------------------------------------------------------------------------
    private String mensagemErro (Response response) {
        // Tenta ler o corpo da resposta HTTP (onde o Microsserviço envia a mensagem detalhada).
        try {
            if (Objects.isNull(response.body())){
                // Se o corpo da resposta for nulo (sem detalhes), retorna uma string vazia.
                return "";
            }
            // Lê todo o conteúdo binário do corpo da resposta (InputStream) e o converte para String.
            // Esta é a forma mais robusta de capturar a mensagem de erro que o outro serviço enviou.
            return new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            // Se houver erro ao ler o corpo (ex: stream já fechado), lança uma exceção de Runtime.
            throw new RuntimeException(e);
        }
    }

}