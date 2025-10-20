package com.EngCode.BFF_Agendador_de_Tarefas.business;

// BLOCÃO 1: IMPORTAÇÕES NECESSÁRIAS PARA ORQUESTRAÇÃO
// -------------------------------------------------------------------------
// DTOs de comunicação.

import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.in.TarefasDTORequest;
import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.out.TarefasDTOResponse;
import com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.client.TarefasClient;
import com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.enums.StatusNotificacaoEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
// ANOTAÇÃO SPRING: Marca a classe como um Componente de Serviço.
@RequiredArgsConstructor
// LOMBOK: Gera o construtor para injeção da dependência 'final' (TarefasClient).
public class TarefasService {

    // BLOCÃO 2: INJEÇÃO DE DEPENDÊNCIA (O Feign Client)
    // -------------------------------------------------------------------------
    private final TarefasClient tarefasClient; // Interface Feign que conecta ao Microsserviço de Agendamento.

    /**
     * MÉTODO: gravarTarefa(String token, TarefasDTO tarefasDTO)
     * FUNÇÃO: Delega a requisição POST de criação de tarefa para o Microsserviço de Agendamento.
     * NATUREZA: Pass-Through com repasse de Token.
     */
    public TarefasDTOResponse gravarTarefa(String token, TarefasDTORequest tarefasDTO) {
        // FUNÇÃO: O BFF não calcula o e-mail nem a data de criação; ele envia o DTO e o Token.
        // O Microsserviço de Agendamento fará toda a lógica (extração do e-mail do token, data, status).
        return tarefasClient.gravarTarefas(tarefasDTO, token);
    }

    /**
     * MÉTODO: buscaTarefasAgendadasPorPeriodo(LocalDateTime, LocalDateTime)
     * FUNÇÃO: Delega a busca de tarefas por período.
     */
    public List<TarefasDTOResponse> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal, String token) {
        // FUNÇÃO: O BFF apenas repassa os parâmetros de data.
        return tarefasClient.buscarListaTarefasPorPeriodo(dataInicial, dataFinal, token);
    }

    /**
     * MÉTODO: buscaTarefasPorEmail(String token)
     * FUNÇÃO: Delega a busca de tarefas do usuário logado.
     */
    public List<TarefasDTOResponse> buscaTarefasPorEmail(String token) {
        // FUNÇÃO: O BFF repassa o Token, e o Microsserviço de Agendamento extrai o e-mail para buscar as tarefas.
        return tarefasClient.buscarListaTarefasPorEmail(token);
    }

    /**
     * MÉTODO: deletaTarefaPorId(String id, String token)
     * FUNÇÃO: Delega a exclusão de tarefa, exigindo o Token para validação de posse.
     */
    public void deletaTarefaPorId(String id, String token) {
        // FUNÇÃO: O BFF repassa o ID e o Token para o Microsserviço de Agendamento validar se o usuário pode deletar.
        tarefasClient.deletaTarefaPorId(id, token);
    }

    /**
     * MÉTODO: alteraStatusDaTarefa(StatusNotificacaoEnum, String id, String token)
     * FUNÇÃO: Delega a atualização parcial do status da tarefa.
     */
    public TarefasDTOResponse alteraStatusDaTarefa(StatusNotificacaoEnum statusNotificacaoEnum, String id, String token) {
        // FUNÇÃO: Repassa o novo status, o ID e o Token de autorização.
        return tarefasClient.alteraStatusDeNotificacao(statusNotificacaoEnum, id, token);
    }

    /**
     * MÉTODO: updateDeTarefas(TarefasDTO, String id, String token)
     * FUNÇÃO: Delega a atualização completa (PUT) de uma tarefa.
     */
    public TarefasDTOResponse updateDeTarefas(TarefasDTORequest tarefasDTO, String id, String token) {

        // FUNÇÃO: O BFF repassa o DTO completo, o ID da tarefa e o Token de autorização.
        // Toda a lógica de merge/atualização é feita no Microsserviço de Agendamento.
        return tarefasClient.updateDeTarefas(tarefasDTO, id, token);
    }
}