package com.EngCode.BFF_Agendador_de_Tarefas.business.dto.in;
// Define o pacote onde esta classe reside. 'business.dto' indica que o propósito da classe
// é atuar como um Objeto de Transferência de Dados (DTO) para a camada de negócio.

import lombok.*;
// Importa todas as anotações do Lombok, usadas para gerar código padrão (boilerplate)
// automaticamente, mantendo a classe limpa.

// BLOCÃO 1: ANOTAÇÕES LOMBOK (Criação e Acesso à Classe)
// -------------------------------------------------------------------------
@Builder
// Gera o Padrão Builder, permitindo a criação do objeto de forma fluida e legível
// (ex: TelefoneDTO.builder().ddd("83").build()).
@Getter
// Gera os métodos 'get' públicos (ex: getNumero()) para leitura de todos os atributos.
@Setter
// Gera os métodos 'set' públicos (ex: setNumero()) para modificação de todos os atributos.
@AllArgsConstructor
// Gera um construtor com todos os atributos.
@NoArgsConstructor
// Gera um construtor vazio. É essencial para que o Spring (na deserialização JSON)
// consiga criar uma instância da classe ao receber dados de uma requisição HTTP.

public class TelefoneDTORequest {
// Esta classe é um DTO: ela apenas transporta dados e não contém lógica de negócio complexa
// nem anotações de persistência (@Entity).

// BLOCÃO 2: ATRIBUTOS (Os Dados a Serem Transferidos)
// -------------------------------------------------------------------------

    private String numero;
    // O número do telefone. Usar String é comum, pois não há operações matemáticas envolvidas.

    private String ddd;
    // O código de área do telefone.
}