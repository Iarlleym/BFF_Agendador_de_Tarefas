package com.EngCode.BFF_Agendador_de_Tarefas.controller;

// BLOCÃO 1: IMPORTAÇÕES E FERRAMENTAS
// -------------------------------------------------------------------------

// DTOs (Data Transfer Objects) que definem o formato de entrada e saída.

import com.EngCode.BFF_Agendador_de_Tarefas.business.UsuarioService;
import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.in.EnderecoDTORequest;
import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.in.LoginDTORequest;
import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.in.TelefoneDTORequest;
import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.in.UsuarioDTORequest;
import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.out.EnderecoDTOResponse;
import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.out.TelefoneDTOResponse;
import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.out.UsuarioDTOResponse;
import com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


// BLOCÃO 2: ESTRUTURA E INJEÇÃO DE DEPENDÊNCIA
// -------------------------------------------------------------------------

@RestController
// ANOTAÇÃO REST: Marca a classe como um controlador que lida com requisições HTTP (API).
@RequestMapping("/usuario")
// Define o caminho base da API (todas as rotas começam com /usuario).
@RequiredArgsConstructor
// LOMBOK: Gera o construtor necessário para injetar a variável 'final' (usuarioService).
@Tag(name = "Usuário", description = "Endpoints de Cadastro, Login e Gerenciamento de Usuários")
// SWAGGER: Define a tag principal que agrupa estas rotas na documentação.
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
public class UsuarioControler {

    // Dependência do Serviço (que fará a comunicação real com o Microsserviço de Usuários).
    private final UsuarioService usuarioService;

    // BLOCÃO 3: ENDPOINTS DE CADASTRO E LOGIN (Públicos)
    // -------------------------------------------------------------------------

    @PostMapping
    // SWAGGER: Documentação do endpoint de Cadastro.
    @Operation(summary = "Salvar Usuários", description = "Cria um novo usuário na base de dados.")
    @ApiResponse(responseCode = "200", description = "Usuário Salvo Com Sucesso.")
    @ApiResponse(responseCode = "400", description = "Dados Inválidos ou E-mail Já Cadastrado.")
    @ApiResponse(responseCode = "500" , description = "Erro de Servidor.")
    public ResponseEntity<UsuarioDTOResponse> salvaUsuario(@RequestBody UsuarioDTORequest usuarioDTO) {
        // FUNÇÃO: Encaminha os dados do DTO para o Microsserviço de Usuários.
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDTO));
    }

    @PostMapping("/login")
    // SWAGGER: Documentação do endpoint de Login.
    @Operation(summary = "Login de Usuários", description = "Autentica o usuário e retorna o Token JWT.")
    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso. Retorna o Token JWT.")
    @ApiResponse(responseCode = "401", description = "Credenciais Inválidas.")
    @ApiResponse(responseCode = "500" , description = "Erro de Servidor.")
    public String login(@RequestBody LoginDTORequest loginDTORequest) {
        // FUNÇÃO: Encaminha as credenciais para o Microsserviço de Usuários e retorna o JWT.
        // CORREÇÃO: Chamada de método do Service corrigida.
        return usuarioService.loginUsuario(loginDTORequest);
    }

    // BLOCÃO 4: ENDPOINTS DE CONSULTA E REMOÇÃO (Protegidos)
    // -------------------------------------------------------------------------

    @GetMapping
    // SWAGGER: Documentação do endpoint de Busca por E-mail.
    @Operation(summary = "Buscar dados de Usuários por E-mail", description = "Busca os dados do usuário usando o Token de Autorização.")
    @ApiResponse(responseCode = "200", description = "Usuário Encontrado.")
    @ApiResponse(responseCode = "401", description = "Não Autorizado (Token Inválido).")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    @ApiResponse(responseCode = "500" , description = "Erro de Servidor.")
    public ResponseEntity<UsuarioDTOResponse> buscaUsuarioPorEmail(
            @RequestParam("email") String email, // E-mail a ser consultado (Query Parameter).
            @RequestHeader(name = "Authorization", required = false) String token) { // Token JWT (Header).
        // FUNÇÃO: O BFF repassa o Token para que o Microsserviço de Usuários valide a autenticação.
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email, token));
    }

    @DeleteMapping("/{email}")
    // SWAGGER: Documentação do endpoint de Remoção.
    @Operation(summary = "Deletar Usuário por E-mail", description = "Deleta o usuário especificado após validação do Token.")
    @ApiResponse(responseCode = "200", description = "Usuário Deletado Com Sucesso.")
    @ApiResponse(responseCode = "401", description = "Não Autorizado (Token Inválido).")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    @ApiResponse(responseCode = "500", description = "Erro de Servidor.")
    public ResponseEntity<Void> deletaUsuarioPorEmail(
            @PathVariable String email, // E-mail a ser deletado (Path Variable).
            @RequestHeader(name = "Authorization", required = false) String token) { // Token JWT (Header).

        usuarioService.deletaUsuarioPorEmail(email, token);

        // Retorna o Status HTTP 200 (OK) sem corpo.
        return ResponseEntity.ok().build();
    }

    // BLOCÃO 5: ENDPOINTS DE ATUALIZAÇÃO (Protegidos)
    // -------------------------------------------------------------------------

    @PutMapping
    // SWAGGER: Documentação do endpoint de Atualização de Dados Principais.
    @Operation(summary = "Atualizar Dados Principais", description = "Atualiza nome, e-mail ou senha do usuário logado (token).")
    @ApiResponse(responseCode = "200", description = "Dados Atualizados Com Sucesso.")
    @ApiResponse(responseCode = "401", description = "Não Autorizado (Token Inválido).")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    @ApiResponse(responseCode = "500", description = "Erro de Servidor.")
    public ResponseEntity<UsuarioDTOResponse> atualizaDadosUsuario(
            @RequestBody UsuarioDTORequest usuarioDTO, // Novos dados.
            @RequestHeader(name = "Authorization", required = false) String token) { // Token JWT.

        return ResponseEntity.ok(usuarioService.atualizaDaddosUsuario(token, usuarioDTO));
    }

    @PutMapping("/endereco")
    // SWAGGER: Documentação do endpoint de Atualização de Endereço.
    @Operation(summary = "Atualizar Endereço", description = "Atualiza um endereço específico pelo ID, verificando o Token.")
    @ApiResponse(responseCode = "200", description = "Endereço Atualizado Com Sucesso.")
    @ApiResponse(responseCode = "401", description = "Não Autorizado (Token Inválido).")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    @ApiResponse(responseCode = "500", description = "Erro de Servidor.")
    public ResponseEntity<EnderecoDTOResponse> atualizaEndereco(
            @RequestBody EnderecoDTORequest enderecoDTO,
            @RequestParam("id") Long id, // ID do endereço a ser atualizado.
            @RequestHeader(name = "Authorization", required = false) String token) {

        return ResponseEntity.ok(usuarioService.atualizaEndereco(id, enderecoDTO, token));
    }

    @PutMapping("/telefone")
    // SWAGGER: Documentação do endpoint de Atualização de Telefone.
    @Operation(summary = "Atualizar Telefone", description = "Atualiza um telefone específico pelo ID, verificando o Token.")
    @ApiResponse(responseCode = "200", description = "Telefone Atualizado Com Sucesso.")
    @ApiResponse(responseCode = "401", description = "Não Autorizado (Token Inválido).")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    @ApiResponse(responseCode = "500", description = "Erro de Servidor.")
    public ResponseEntity<TelefoneDTOResponse> atualizaTelefone(
            @RequestBody TelefoneDTORequest telefoneDTO,
            @RequestParam("id") Long id, // ID do telefone a ser atualizado.
            @RequestHeader(name = "Authorization", required = false) String token) {

        return ResponseEntity.ok(usuarioService.atualizaTelefones(id, telefoneDTO, token));
    }

    // BLOCÃO 6: ENDPOINTS DE CRIAÇÃO DE RECURSOS ANINHADOS (Protegidos)
    // -------------------------------------------------------------------------

    @PostMapping("/endereco")
    // SWAGGER: Documentação do endpoint de Criação de Endereço Aninhado.
    @Operation(summary = "Cadastrar Novo Endereço", description = "Associa um novo endereço ao usuário autenticado pelo Token.")
    @ApiResponse(responseCode = "200", description = "Endereço Cadastrado Com Sucesso.")
    @ApiResponse(responseCode = "401", description = "Não Autorizado (Token Inválido).")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    @ApiResponse(responseCode = "500", description = "Erro de Servidor.")
    public ResponseEntity<EnderecoDTOResponse> cadastraEndereco(
            @RequestBody EnderecoDTORequest enderecoDTO,
            @RequestHeader(name = "Authorization", required = false) String token) {

        return ResponseEntity.ok(usuarioService.cadastraEndereco(token, enderecoDTO));
    }


    @PostMapping("/telefone")
    // SWAGGER: Documentação do endpoint de Criação de Telefone Aninhado.
    @Operation(summary = "Cadastrar Novo Telefone", description = "Associa um novo telefone ao usuário autenticado pelo Token.")
    @ApiResponse(responseCode = "200", description = "Telefone Cadastrado Com Sucesso.")
    @ApiResponse(responseCode = "401", description = "Não Autorizado (Token Inválido).")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    @ApiResponse(responseCode = "500", description = "Erro de Servidor.")
    public ResponseEntity<TelefoneDTOResponse> cadastraTelefone(
            @RequestBody TelefoneDTORequest telefoneDTO,
            @RequestHeader(name = "Authorization", required = false) String token) {

        return ResponseEntity.ok(usuarioService.cadastraTelefone(token, telefoneDTO));
    }
}