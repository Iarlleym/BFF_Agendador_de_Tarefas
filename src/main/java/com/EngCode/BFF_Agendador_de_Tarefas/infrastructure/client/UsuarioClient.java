package com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.client;

// BLOCÃO 1: IMPORTAÇÕES E DTOs
// -------------------------------------------------------------------------

// Importa os DTOs, que são os formatos JSON esperados na comunicação HTTP.

import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.in.EnderecoDTORequest;
import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.in.LoginDTORequest;
import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.in.TelefoneDTORequest;
import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.in.UsuarioDTORequest;
import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.out.EnderecoDTOResponse;
import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.out.TelefoneDTOResponse;
import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.out.UsuarioDTOResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 🔹 Interface responsável por fazer a comunicação com outro serviço (microserviço de usuários)
 * usando o Feign Client — uma ferramenta que facilita chamadas HTTP entre APIs no Spring Boot.
 *
 * Em resumo: essa interface permite buscar dados de um usuário em outro sistema,
 * apenas chamando um método Java (sem precisar escrever código HTTP manualmente).
 */

// BLOCÃO 2: CONFIGURAÇÃO DO FEIGN CLIENT
// -------------------------------------------------------------------------
@FeignClient(name = "usuario", url = "${usuario.url}")
// ANOTAÇÃO FEIGN: Marca esta interface como um cliente REST que o Spring deve implementar automaticamente.
// name = "usuario": Nome lógico do serviço (usado internamente).
// url = "${usuario.url}": Endereço base do Microsserviço de Usuários, puxado de uma variável no application.properties (ex: http://localhost:8081).
public interface UsuarioClient {
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
    @GetMapping ("/usuario")
    // Mapeia o método Java para uma requisição HTTP GET no caminho "/usuario".
    UsuarioDTOResponse buscaUsuarioPorEmail(
            @RequestParam("email") String email,          // @RequestParam: Indica que 'email' deve ser passado como Query Parameter na URL.
            @RequestHeader("Authorization") String token  // @RequestHeader: Indica que 'token' deve ser enviado no cabeçalho HTTP "Authorization". ESSENCIAL para segurança.
    );

    // BLOCÃO 4: ENDPOINTS DE CRIAÇÃO E LOGIN (POST)
    // -------------------------------------------------------------------------

    @PostMapping ("/usuario")
        // Mapeia requisições HTTP POST para /usuario. Usado para Cadastro.
    UsuarioDTOResponse salvaUsuario(@RequestBody UsuarioDTORequest usuarioDTO);
    // @RequestBody: O objeto usuarioDTO será serializado para JSON e enviado no corpo da requisição.

    @PostMapping("/usuario/login")
        // Mapeia requisições HTTP POST para /usuario/login.
    String login(@RequestBody LoginDTORequest loginDTORequest);
    // Retorno: Espera-se uma String simples do Token JWT retornado pelo outro Microsserviço.

    // BLOCÃO 5: ENDPOINTS DE DELEÇÃO (DELETE)
    // -------------------------------------------------------------------------

    @DeleteMapping("/usuario/{email}")
        // Mapeia requisições HTTP DELETE para /usuario/{email} (o e-mail vai na URL como Path Variable).
    void deletaUsuarioPorEmail(
            @PathVariable String email, // @PathVariable: O valor de 'email' é inserido diretamente na URL mapeada.
            @RequestHeader("Authorization") String token); // Repasse do Token.

    // BLOCÃO 6: ENDPOINTS DE ATUALIZAÇÃO (PUT)
    // -------------------------------------------------------------------------

    @PutMapping ("/usuario")
        // Mapeia requisições HTTP PUT para /usuario (Atualização de dados principais).
    UsuarioDTOResponse atualizaDadosUsuario(
            @RequestBody UsuarioDTORequest usuarioDTO, // Corpo da requisição com os novos dados.
            @RequestHeader("Authorization") String token); // Token de autorização.

    @PutMapping("/usuario/endereco")
        // Mapeia PUT /usuario/endereco. Atualiza um recurso aninhado (Endereço).
    EnderecoDTOResponse atualizaEndereco(
            @RequestBody EnderecoDTORequest enderecoDTO,
            @RequestParam("id") Long id, // ID do endereço a ser atualizado (passado como Query Parameter).
            @RequestHeader("Authorization") String token);

    @PutMapping("/usuario/telefone")
        // Mapeia PUT /usuario/telefone. Atualiza um Telefone.
    TelefoneDTOResponse atualizaTelefone(
            @RequestBody TelefoneDTORequest telefoneDTO,
            @RequestParam("id") Long id, // ID do telefone (passado como Query Parameter).
            @RequestHeader("Authorization") String token);

    // BLOCÃO 7: ENDPOINTS DE CRIAÇÃO DE RECURSOS ANINHADOS (Protegidos POST)
    // -------------------------------------------------------------------------

    @PostMapping("usuario/endereco")
        // Mapeia POST /usuario/endereco. Cria um novo Endereço para o usuário logado.
    EnderecoDTOResponse cadastraEndereco(
            @RequestBody EnderecoDTORequest enderecoDTO,
            @RequestHeader("Authorization") String token); // Token para identificar o usuário.

    @PostMapping("usuario/telefone")
        // Mapeia POST /usuario/telefone. Cria um novo Telefone para o usuário logado.
    TelefoneDTOResponse cadastraTelefone(
            @RequestBody TelefoneDTORequest telefoneDTO,
            @RequestHeader("Authorization") String token); // Token para identificar o usuário.

}