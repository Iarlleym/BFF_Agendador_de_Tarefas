package com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.client;

// BLOCÃO 1: IMPORTAÇÕES E DTOs
// -------------------------------------------------------------------------

// Importa os DTOs, que são os formatos JSON esperados na comunicação HTTP.

import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.in.TarefasDTORequest;
import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.out.TarefasDTOResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 🔹 Interface responsável por fazer a comunicação com outro serviço (microserviço de usuários)
 * usando o Feign Client — uma ferramenta que facilita chamadas HTTP entre APIs no Spring Boot.
 *
 * Em resumo: essa interface permite buscar dados de um usuário em outro sistema,
 * apenas chamando um método Java (sem precisar escrever código HTTP manualmente).
 */

// BLOCÃO 2: CONFIGURAÇÃO DO FEIGN CLIENT
// -------------------------------------------------------------------------
@FeignClient(name = "notificacao", url = "${notificacao.url}")
// ANOTAÇÃO FEIGN: Marca esta interface como um cliente REST que o Spring deve implementar automaticamente.
// name = "usuario": Nome lógico do serviço (usado internamente).
// url = "${usuario.url}": Endereço base do Microsserviço de Usuários, puxado de uma variável no application.properties (ex: http://localhost:8081).
public interface EmailClient {
// Feign Clients são sempre interfaces.

    // BLOCÃO 3: ENDPOINTS DE CONSULTA (GET)
    // -------------------------------------------------------------------------

    /**
     * 🔹 Método responsável por buscar um usuário a partir do e-mail informado.
     * Esse método faz uma requisição GET para o endpoint "/usuario" do outro serviço.
     *
     * Exemplo de chamada:
     * GET http://servidor/usuario?email=teste@mail.com
     * Header: Authorization: Bearer tokenAqui
     */
     void enviarEmail (@RequestBody TarefasDTOResponse tarefasDTOResponse);
}