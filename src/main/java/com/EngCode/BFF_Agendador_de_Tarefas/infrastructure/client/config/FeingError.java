package com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.client.config;

// BLOCÃO 1: IMPORTAÇÕES E EXCEÇÕES NECESSÁRIAS
// -------------------------------------------------------------------------
import com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.exceptions.BusinessException;
import com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.exceptions.ConflictException;
import com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.exceptions.ResourceNotFoundException;
import com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.exceptions.UnauthorizedException;
import feign.Response;
import feign.codec.ErrorDecoder;
import feign.FeignException; // Importado para tratamento de erros genéricos

public class FeingError implements ErrorDecoder {
// IMPLEMENTAÇÃO: Esta interface é obrigatória para personalizar como o Feign trata os erros HTTP.

    @Override
    // MÉTODO OBRIGATÓRIO: Chamado pelo Feign sempre que recebe um código HTTP de erro (4xx ou 5xx).
    // String s: O nome do método Feign que falhou (útil para logs).
    // Response response: O objeto de resposta HTTP (contém o status, headers e corpo).
    public Exception decode(String s, Response response) {

        // Vamos tentar ler a mensagem do corpo da resposta (se houver) para torná-lo mais informativo.
        String erroPadrao = "Erro na comunicação com o Microsserviço de Tarefas.";

        // Estratégia de Fallback: Se a resposta do outro serviço incluir uma mensagem no cabeçalho, a usamos.
        String mensagemDetalhada = response.reason() != null ? response.reason() : erroPadrao;


        switch (response.status()) {

            // -----------------------------------------------------------
            // TRATAMENTO DE ERROS MAIS COMUNS (COMPLETO)
            // -----------------------------------------------------------

            case 400: // Bad Request (Requisição Malfeita)
                // Lançado quando a validação falha no microsserviço de destino (ex: campos obrigatórios ausentes).
                // MENSAGEM MELHORADA: Foca no erro do cliente.
                return new BusinessException(
                        "Erro 400 - Requisição Inválida: Verifique os dados enviados.");

            case 401: // Unauthorized (Não Autorizado)
                // Lançado quando o Token JWT está ausente, é inválido ou expirou.
                // MENSAGEM MELHORADA: Mais específica sobre o problema de autenticação.
                return new UnauthorizedException(
                        "Erro 401: Acesso Negado. Token JWT inválido ou ausente.");

            case 403: // Forbidden (Acesso Proibido)
                // Lançado quando o usuário está logado, mas não tem permissão para acessar aquele recurso (ex: tentar deletar a tarefa de outro usuário).
                // MENSAGEM MELHORADA: Uso do 403 para indicar a falha na autorização.
                return new UnauthorizedException(
                        "Erro 403: Usuário não autorizado a realizar esta operação neste recurso.");

            case 404: // Not Found (Recurso não Encontrado)
                // Lançado quando a tarefa ou recurso buscado não existe (ex: buscar por ID inexistente).
                // MENSAGEM MELHORADA: Mais específica sobre a natureza do erro.
                return new ResourceNotFoundException(
                        "Erro 404: O recurso solicitado (Tarefa ou ID) não foi encontrado.");

            case 409: // Conflict (Conflito) - SEU CASO ORIGINAL
                // Lançado quando há conflito de dados (ex: tarefa com nome duplicado se houver regra).
                // MENSAGEM MELHORADA: Direto ao ponto.
                return new ConflictException(
                        "Erro 409: Conflito de dados. O atributo enviado já está registrado.");

            case 500: // Internal Server Error
                // Lançado para falhas internas inesperadas no Microsserviço de Agendamento.
                return new BusinessException(
                        "Erro 500: Falha interna inesperada no Microsserviço de Agendamento de Tarefas.");

            default:
                // Retorno Padrão: Para qualquer outro código (ex: 502, 503, etc.),
                // o Feign lança a exceção genérica.
                return new BusinessException("Erro Desconhecido");
        }

    }
}