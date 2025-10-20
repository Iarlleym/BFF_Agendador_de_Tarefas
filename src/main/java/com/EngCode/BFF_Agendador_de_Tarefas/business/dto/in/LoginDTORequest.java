package com.EngCode.BFF_Agendador_de_Tarefas.business.dto.in;

import lombok.*;

// BLOCÃO 1: ANOTAÇÕES DO LOMBOK (Estrutura da Classe)
// -------------------------------------------------------------------------
@Builder
// Gera o Padrão Builder, que permite criar instâncias de UsuarioDTO de forma
// organizada e clara no UsuarioConverter (ex: UsuarioDTO.builder()...build()).
@Getter
// Gera os métodos 'get' públicos (leitura de atributos).
@Setter
// Gera os métodos 'set' públicos (modificação de atributos).
@AllArgsConstructor
// Gera um construtor que aceita todos os atributos como parâmetros.
@NoArgsConstructor
// Gera um construtor vazio. Isso é essencial para que o Spring consiga desserializar

public class LoginDTORequest {

    private String email;
    // Campo simples. É comum usá-lo também como login (username) no Spring Security.

    private String senha;
    // Campo crítico. É obrigatório na ENTRADA (cadastro/login). Na SAÍDA (resposta da API),
    // este campo deve ser omitido ou limpo por questões de segurança.

}
