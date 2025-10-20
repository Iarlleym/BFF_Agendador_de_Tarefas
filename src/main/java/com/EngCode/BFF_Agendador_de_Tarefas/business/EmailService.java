package com.EngCode.BFF_Agendador_de_Tarefas.business;

import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.out.TarefasDTOResponse;
import com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.client.EmailClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service // ANOTAÇÃO SPRING: Marca esta classe como um Componente de Serviço, contendo a lógica de negócio principal.
@RequiredArgsConstructor // LOMBOK: Gera um construtor com todos os campos 'final', essencial para a Injeção de Dependência.
public class EmailService {

    // INJEÇÃO DE DEPENDÊNCIA: O Spring inicializa e fornece estas duas ferramentas essenciais.
    private final EmailClient emailClient;

    // MÉTODO PRINCIPAL: Recebe o DTO (objeto de dados) da tarefa e inicia o envio.
    public void enviaEmail (TarefasDTOResponse tarefasDTOResponse) {

      emailClient.enviarEmail(tarefasDTOResponse);

    }

}