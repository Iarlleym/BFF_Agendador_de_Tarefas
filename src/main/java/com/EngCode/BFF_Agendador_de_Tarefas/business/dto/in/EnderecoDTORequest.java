package com.EngCode.BFF_Agendador_de_Tarefas.business.dto.in;
// Define o pacote onde esta classe reside. Estar no 'business.dto' indica
// que é uma classe de comunicação da camada de negócio (API).

import lombok.*;
// Importa todas as anotações do Lombok, que reduzem a necessidade de escrever
// código repetitivo (getters, setters, construtores, etc.).

// BLOCÃO 1: ANOTAÇÕES DO LOMBOK (A Construção da Classe)
// -------------------------------------------------------------------------
@Builder
// Gera o Padrão Builder. Isso permite criar objetos de forma clara no Converter
// (ex: EnderecoDTO.builder().rua("...").build()). É limpo e organizado.
@Getter
// Gera automaticamente os métodos 'get' para leitura de cada atributo (ex: getRua()).
@Setter
// Gera automaticamente os métodos 'set' para modificação de cada atributo (ex: setRua()).
@AllArgsConstructor
// Gera um construtor que aceita todos os atributos como parâmetros.
@NoArgsConstructor
// Gera um construtor vazio. Isso é CRUCIAL para o Spring conseguir reconstruir
// o objeto a partir do JSON recebido na requisição HTTP.

public class EnderecoDTORequest {
// DTOs são classes simples, sem lógica complexa ou anotações de persistência (@Entity).

// BLOCÃO 2: ATRIBUTOS (Os Dados)
// -------------------------------------------------------------------------

    private String rua;
    private Long numero; // Usa Long para o número, para consistência numérica.
    private String complemento;
    private String cidade;
    private String estado;
    private String cep;
    // Todos os atributos representam os dados que serão mapeados.
    // DICA: Em DTOs de entrada, você pode adicionar anotações de validação (ex: @NotBlank) aqui.
}