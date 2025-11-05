package com.EngCode.BFF_Agendador_de_Tarefas.business.dto.out;

import lombok.*;

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

public class ViaCepDTORespose {

    public String cep;
    public String logradouro;
    public String complemento;
    public String unidade;
    public String bairro;
    public String localidade;
    public String uf;
    public String estado;
    public String regiao;
    public String ibge;
    public String gia;
    public String ddd;
    public String siafi;
}

