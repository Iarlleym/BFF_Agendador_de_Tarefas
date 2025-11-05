package com.EngCode.BFF_Agendador_de_Tarefas.business;

// BLOCÃO 1: IMPORTAÇÕES DE FERRAMENTAS E DEPENDÊNCIAS
// -------------------------------------------------------------------------

// DTOs (Objetos de Transferência de Dados)

import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.in.EnderecoDTORequest;
import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.in.LoginDTORequest;
import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.in.TelefoneDTORequest;
import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.in.UsuarioDTORequest;
import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.out.EnderecoDTOResponse;
import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.out.TelefoneDTOResponse;
import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.out.UsuarioDTOResponse;
import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.out.ViaCepDTORespose;
import com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.client.UsuarioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
// ANOTAÇÃO SPRING: Marca a classe como um Componente de Serviço, contendo a lógica de orquestração.
@RequiredArgsConstructor
// LOMBOK: Gera um construtor que injeta o 'UsuarioClient'.
public class UsuarioService {

    // BLOCÃO 2: INJEÇÃO DE DEPENDÊNCIA (A Única Ferramenta)
    // -------------------------------------------------------------------------
    private final UsuarioClient usuarioClient;
    // VARIÁVEL CRÍTICA: O Feign Client. O Service delega TODAS as responsabilidades a esta interface.

    /**
     * MÉTODO: salvaUsuario(UsuarioDTO)
     * FUNÇÃO: Encaminha os dados de cadastro para o Microsserviço de Usuários.
     * NATUREZA: Pass-Through (passagem direta).
     */
    public UsuarioDTOResponse salvaUsuario(UsuarioDTORequest usuarioDTO) {
        // FUNÇÃO: O Service chama o método correspondente na interface Feign. O Feign lida
        // com a serialização para JSON e o envio da requisição POST para o Microsserviço de Usuários.
        return usuarioClient.salvaUsuario(usuarioDTO);
    }

    /**
     * MÉTODO: loginUsuario(UsuarioDTO)
     * FUNÇÃO: Executa o login e retorna o Token JWT.
     */
    public String loginUsuario (LoginDTORequest loginDTORequest) {
        // FUNÇÃO: O Service encaminha o DTO de login e espera que o Feign retorne o JWT como String.
        return usuarioClient.login(loginDTORequest);
    }

    /**
     * MÉTODO: buscarUsuarioPorEmail(String, String)
     * FUNÇÃO: Busca um usuário por e-mail, repassando o Token de segurança.
     */
    public UsuarioDTOResponse buscarUsuarioPorEmail (String email, String token) {
        // FUNÇÃO: Chama o Feign, repassando os parâmetros de busca (email) e o cabeçalho de segurança (token).
        return usuarioClient.buscaUsuarioPorEmail(email, token);
    }

    /**
     * MÉTODO: deletaUsuarioPorEmail(String, String)
     * FUNÇÃO: Remove um usuário, repassando o Token de segurança.
     */
    public void deletaUsuarioPorEmail (String email, String token) {
        // FUNÇÃO: Delega a requisição DELETE para o Feign.
        usuarioClient.deletaUsuarioPorEmail(email, token);
    }

    /**
     * MÉTODO: atualizaDaddosUsuario(String, UsuarioDTO)
     * FUNÇÃO: Atualiza os dados principais do usuário logado.
     */
    public UsuarioDTOResponse atualizaDaddosUsuario (String token, UsuarioDTORequest usuarioDTO) {
        // FUNÇÃO: O BFF chama o método PUT correspondente no Feign. Note que a ordem dos argumentos
        // no Service do BFF deve corresponder à ordem esperada no Cliente Feign.
        return usuarioClient.atualizaDadosUsuario(usuarioDTO, token);
    }

    /**
     * MÉTODO: atualizaEndereco(Long, EnderecoDTO, String)
     * FUNÇÃO: Atualiza um endereço específico.
     */
    public EnderecoDTOResponse atualizaEndereco(Long idEndereco, EnderecoDTORequest enderecoDTO, String token) {
        // FUNÇÃO: Delega a requisição PUT, repassando DTO, ID do endereço e Token.
        return usuarioClient.atualizaEndereco(enderecoDTO, idEndereco, token);
    }

    /**
     * MÉTODO: atualizaTelefones(Long, TelefoneDTO, String)
     * FUNÇÃO: Atualiza um telefone específico.
     */
    public TelefoneDTOResponse atualizaTelefones(Long idTelefone, TelefoneDTORequest telefoneDTO, String token) {
        // FUNÇÃO: Delega a requisição PUT, repassando DTO, ID do telefone e Token.
        return usuarioClient.atualizaTelefone(telefoneDTO, idTelefone, token);
    }

    /**
     * MÉTODO: cadastraEndereco(String, EnderecoDTO)
     * FUNÇÃO: Adiciona um novo endereço associado ao usuário logado.
     */
    public EnderecoDTOResponse cadastraEndereco (String token, EnderecoDTORequest enderecoDTO) {
        // FUNÇÃO: Delega a requisição POST para o Feign, enviando os dados e o Token.
        return usuarioClient.cadastraEndereco(enderecoDTO, token);
    }

    /**
     * MÉTODO: cadastraTelefone(String, TelefoneDTO)
     * FUNÇÃO: Adiciona um novo telefone associado ao usuário logado.
     */
    public TelefoneDTOResponse cadastraTelefone (String token, TelefoneDTORequest telefoneDTO) {
        // FUNÇÃO: Delega a requisição POST para o Feign, enviando os dados e o Token.
        return usuarioClient.cadastraTelefone(telefoneDTO, token);
    }

    public ViaCepDTORespose buscarEnderecoViaCep (String cep) {
        return usuarioClient.buscarDadosDeCep(cep);
    }

}