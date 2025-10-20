package com.EngCode.BFF_Agendador_de_Tarefas.controller;

// BLOCÃO 1: IMPORTAÇÕES E FERRAMENTAS
// -------------------------------------------------------------------------

import com.EngCode.BFF_Agendador_de_Tarefas.business.TarefasService;
import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.in.TarefasDTORequest;
import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.out.TarefasDTOResponse;
import com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

// BLOCÃO 2: ESTRUTURA E INJEÇÃO DE DEPENDÊNCIA
// -------------------------------------------------------------------------

@RestController
// ANOTAÇÃO REST: Marca a classe como um Controller que lida com requisições HTTP REST.
@RequestMapping("/tarefas") // Define o endpoint base (/tarefas)
@RequiredArgsConstructor // Lombok: Cria o construtor com as dependências obrigatórias (final)
@Tag(name = "Tarefas", description = "Endpoints para gerenciamento e consulta de tarefas agendadas.")
// SWAGGER: Define a tag que agrupa todas as rotas de Tarefas na documentação.
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
public class TarefasController {

    private final TarefasService tarefasService; // Injeta o serviço do BFF que contém a lógica de orquestração (FeignClient)

    // BLOCÃO 3: ENDPOINTS DE CRIAÇÃO (POST)
    // -------------------------------------------------------------------------

    @PostMapping
    // SWAGGER: Documentação do endpoint de Cadastro.
    @Operation(summary = "Cadastrar Tarefa", description = "Cria uma nova tarefa associada ao usuário logado.")
    @ApiResponse(responseCode = "200", description = "Tarefa Gravada Com Sucesso.")
    @ApiResponse(responseCode = "401", description = "Não Autorizado (Token Inválido).")
    @ApiResponse(responseCode = "500", description = "Erro de Servidor.")
    public ResponseEntity<TarefasDTOResponse> gravarTarefas(@RequestBody TarefasDTORequest tarefasDTO,
                                                            @RequestHeader(name = "Authorization", required = false) String token) {
        // @RequestHeader("Authorization") String token: Captura o Token JWT para repasse.
        // FUNÇÃO: O BFF repassa a requisição para o Microsserviço de Tarefas.
        return ResponseEntity.ok(tarefasService.gravarTarefa(token, tarefasDTO)); // Chama o service e retorna o DTO salvo
    }

    // BLOCÃO 4: ENDPOINTS DE CONSULTA (GET)
    // -------------------------------------------------------------------------

    @GetMapping("/eventos")
    // SWAGGER: Documentação do endpoint de Busca por Período.
    @Operation(summary = "Buscar Tarefas por Período", description = "Retorna tarefas agendadas entre uma data inicial e uma data final.")
    @ApiResponse(responseCode = "200", description = "Lista de Tarefas Retornada Com Sucesso.")
    @ApiResponse(responseCode = "401", description = "Não Autorizado (Token Inválido).")
    @ApiResponse(responseCode = "500", description = "Erro de Servidor.")
    public ResponseEntity<List<TarefasDTOResponse>> buscarListaTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial, // Data inicial
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal,// Data final
            @RequestHeader(name = "Authorization", required = false) String token
    ) {
        // FUNÇÃO: Encaminha os parâmetros de data para o serviço do BFF. Não precisa de token aqui se o endpoint original for público ou se a validação for no Service.
        return ResponseEntity.ok(tarefasService.buscaTarefasAgendadasPorPeriodo(dataInicial, dataFinal, token)); // Retorna lista filtrada
    }

    @GetMapping
    // SWAGGER: Documentação do endpoint de Busca por Usuário.
    @Operation(summary = "Buscar Tarefas do Usuário Logado", description = "Retorna todas as tarefas associadas ao usuário cujo token foi fornecido.")
    @ApiResponse(responseCode = "200", description = "Lista de Tarefas Retornada Com Sucesso.")
    @ApiResponse(responseCode = "401", description = "Não Autorizado (Token Inválido).")
    @ApiResponse(responseCode = "500", description = "Erro de Servidor.")
    public ResponseEntity<List<TarefasDTOResponse>> buscarListaTarefasPorEmail(
            @RequestHeader(name = "Authorization", required = false) String token) { // Captura o Token para identificar o usuário.

        // FUNÇÃO: O BFF repassa o token para o Microsserviço de Tarefas que extrai o e-mail/ID.
        return ResponseEntity.ok(tarefasService.buscaTarefasPorEmail(token)); // Retorna lista de tarefas do usuário
    }

    // BLOCÃO 5: ENDPOINTS DE REMOÇÃO, ATUALIZAÇÃO E PATCH
    // -------------------------------------------------------------------------

    @DeleteMapping
    // SWAGGER: Documentação do endpoint de Exclusão.
    @Operation(summary = "Excluir Tarefa por ID", description = "Deleta uma tarefa específica. Requer Token para autorização.")
    @ApiResponse(responseCode = "200", description = "Tarefa Deletada Com Sucesso.")
    @ApiResponse(responseCode = "401", description = "Não Autorizado (Token Inválido).")
    @ApiResponse(responseCode = "404", description = "Tarefa não encontrada.")
    @ApiResponse(responseCode = "500", description = "Erro de Servidor.")
    public ResponseEntity<Void> deletaTarefaPorId(@RequestParam("id") String id,
                                                  @RequestHeader(name = "Authorization", required = false) String token) { // Adicionando repasse do token

        tarefasService.deletaTarefaPorId(id, token); // Chama o método de exclusão com o Token
        return ResponseEntity.ok().build();   // Retorna resposta vazia (200 OK)
    }

    @PatchMapping
    // SWAGGER: Documentação do endpoint de Patch (Atualização Parcial).
    @Operation(summary = "Alterar Status de Notificação", description = "Atualiza o status de notificação da tarefa (Ex: PENDENTE para ENVIADA). Requer Token.")
    @ApiResponse(responseCode = "200", description = "Status Atualizado Com Sucesso.")
    @ApiResponse(responseCode = "401", description = "Não Autorizado (Token Inválido).")
    @ApiResponse(responseCode = "404", description = "Tarefa não encontrada.")
    @ApiResponse(responseCode = "500", description = "Erro de Servidor.")
    public ResponseEntity<TarefasDTOResponse> alteraStatusDeNotificacao(
            @RequestParam("status") StatusNotificacaoEnum statusNotificacaoEnum, // Novo status (Enum)
            @RequestParam("id") String id, // ID da tarefa
            @RequestHeader(name = "Authorization", required = false) String token) { // Adicionando repasse do token

        // FUNÇÃO: O BFF repassa o status, o ID e o token para o Microsserviço de Tarefas.
        return ResponseEntity.ok(tarefasService.alteraStatusDaTarefa(statusNotificacaoEnum, id, token)); // Retorna tarefa atualizada
    }

    @PutMapping
    // SWAGGER: Documentação do endpoint de Atualização Completa.
    @Operation(summary = "Atualização Completa da Tarefa", description = "Atualiza todos os dados de uma tarefa (título, descrição, etc.). Requer Token.")
    @ApiResponse(responseCode = "200", description = "Tarefa Atualizada Com Sucesso.")
    @ApiResponse(responseCode = "401", description = "Não Autorizado (Token Inválido).")
    @ApiResponse(responseCode = "404", description = "Tarefa não encontrada.")
    @ApiResponse(responseCode = "500", description = "Erro de Servidor.")
    public ResponseEntity<TarefasDTOResponse> updateDeTarefas(@RequestBody TarefasDTORequest tarefasDTO,
                                                              @RequestParam("id") String id,
                                                              @RequestHeader(name = "Authorization", required = false) String token) { // Adicionando repasse do token

        // FUNÇÃO: O BFF envia o DTO completo, o ID e o Token de autorização.
        return ResponseEntity.ok(tarefasService.updateDeTarefas(tarefasDTO, id, token)); // Retorna DTO atualizado após o save
    }
}