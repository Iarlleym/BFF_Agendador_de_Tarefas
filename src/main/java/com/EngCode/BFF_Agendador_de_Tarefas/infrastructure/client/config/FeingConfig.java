package com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.client.config;
// Define o pacote de configuração do cliente Feign, dentro da camada de infraestrutura.

import org.springframework.context.annotation.Bean;
// Importa a anotação @Bean, usada para que o Spring gerencie o objeto retornado pelo método.
import org.springframework.context.annotation.Configuration;
// Importa a anotação @Configuration, que marca a classe como fonte de definições de beans.

@Configuration
// ANOTAÇÃO SPRING: Marca esta classe como uma classe de configuração.
// O Spring a lerá durante a inicialização para criar os objetos (@Bean) definidos aqui.
public class FeingConfig {

    // NOTA: O nome correto da classe e dos métodos Feign é com 'g' (Feign, FeignError),
    // mas mantemos sua escrita original ('Feing') para consistência do seu código.

    @Bean
    // ANOTAÇÃO SPRING: Diz ao Spring para criar, gerenciar e injetar o objeto retornado por este método.
    // FUNÇÃO: Sobrescreve a configuração padrão de tratamento de erros do Feign.
    public FeingError feingError () {
        // O nome do método deve ser 'errorDecoder' (padrão Feign) para ser reconhecido automaticamente,
        // mas o Spring ainda pode reconhecer se o tipo de retorno for o correto (ErrorDecoder).

        // FUNÇÃO: Cria uma nova instância da sua classe personalizada que irá decodificar os erros HTTP.
        return new FeingError();
    }

}