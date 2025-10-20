package com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.client;

// BLOCÃO 1: IMPORTAÇÕES E DTOs
// -------------------------------------------------------------------------

// Importa os DTOs, que definem o formato de dados esperado na comunicação HTTP.

import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.in.TarefasDTORequest;
import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.out.TarefasDTOResponse;
import com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.enums.StatusNotificacaoEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface que define o contrato de comunicação com o Microsserviço de Agendamento de Tarefas.
 * FUNÇÃO: Atua como um proxy declarativo, onde cada método Java mapeia um endpoint HTTP real no outro serviço.
 */

// BLOCÃO 2: CONFIGURAÇÃO DO FEIGN CLIENT
// -------------------------------------------------------------------------
@FeignClient(name = "agendador-tarefas", url = "${agendador-tarefas.url}")
// ANOTAÇÃO FEIGN: O Spring Cloud implementará esta interface automaticamente.
// name: Nome lógico do serviço (para o Spring).
// url: Endereço base do Microsserviço de Agendamento, puxado do application.properties.
public interface TarefasClient {

    // BLOCÃO 3: ENDPOINTS DE CRIAÇÃO (POST)
    // -------------------------------------------------------------------------

    @PostMapping
    TarefasDTOResponse gravarTarefas(
            @RequestBody TarefasDTORequest tarefasDTO, // DTO a ser serializado para JSON no corpo da requisição.
            @RequestHeader("Authorization") String token); // Token JWT repassado no Header para segurança.

    // BLOCÃO 4: ENDPOINTS DE CONSULTA (GET)
    // -------------------------------------------------------------------------

    @GetMapping("/eventos")
    List<TarefasDTOResponse> buscarListaTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal,
            @RequestHeader("Authorization") String token);
    // FUNÇÃO: Mapeia a busca por período, passando as datas como Query Parameters na URL.

    @GetMapping
    List<TarefasDTOResponse> buscarListaTarefasPorEmail(
            @RequestHeader("Authorization") String token); // Token JWT obrigatório para identificar o usuário logado.

    // BLOCÃO 5: ENDPOINTS DE REMOÇÃO, ATUALIZAÇÃO E PATCH
    // -------------------------------------------------------------------------

    @DeleteMapping
    void deletaTarefaPorId(
            @RequestParam("id") String id,
            @RequestHeader("Authorization") String token); // Repassa o Token para autorizar a exclusão.

    @PatchMapping
    TarefasDTOResponse alteraStatusDeNotificacao(
            @RequestParam("status") StatusNotificacaoEnum statusNotificacaoEnum, // Mapeia o Enum para a URL.
            @RequestParam("id") String id,
            @RequestHeader("Authorization") String token); // Repasse do Token.

    @PutMapping
    TarefasDTOResponse updateDeTarefas(
            @RequestBody TarefasDTORequest tarefasDTO, // DTO com os novos dados.
            @RequestParam("id") String id,
            @RequestHeader("Authorization") String token); // Repasse do Token.

}