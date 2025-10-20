package com.EngCode.BFF_Agendador_de_Tarefas.business.dto.in;
// Define o pacote onde esta classe reside. Estar em 'business.dto' reforça que
// o objetivo é transferir dados, isolando-a da camada de persistência.

import lombok.*;

import java.util.List;
// Importa a interface List, usada para definir coleções (como Endereços e Telefones).

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
// (converter) o JSON recebido na requisição HTTP em um objeto Java.

public class UsuarioDTORequest {
// Esta classe é puramente um contêiner de dados (DTO); não possui lógica de negócio
// nem anotações de banco de dados (@Entity).

// BLOCÃO 2: ATRIBUTOS (Os Dados de Comunicação)
// -------------------------------------------------------------------------

    private String nome;
    // Campo simples. Usado tanto na entrada (cadastro) quanto na saída (consulta).

    private String email;
    // Campo simples. É comum usá-lo também como login (username) no Spring Security.

    private String senha;
    // Campo crítico. É obrigatório na ENTRADA (cadastro/login). Na SAÍDA (resposta da API),
    // este campo deve ser omitido ou limpo por questões de segurança.

    private List<EnderecoDTORequest> enderecos;
    // Relacionamento Um-para-Muitos: Uma lista de objetos DTO aninhados.
    // O UsuarioConverter deve ser capaz de converter esta lista de DTOs para Entities.

    private List<TelefoneDTORequest> telefones;
    // Relacionamento Um-para-Muitos: Outra lista de objetos DTO aninhados.
}