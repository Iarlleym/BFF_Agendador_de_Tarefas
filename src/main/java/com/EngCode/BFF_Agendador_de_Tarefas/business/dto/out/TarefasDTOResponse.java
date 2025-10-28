// Classe DTO (Data Transfer Object) usada para transportar dados das tarefas entre as camadas da aplicação.
// O DTO é responsável apenas por representar as informações da tarefa, sem conter regras de negócio.
// Essa classe usa anotações do Lombok para reduzir código repetitivo, e também formata as datas para JSON.

package com.EngCode.BFF_Agendador_de_Tarefas.business.dto.out;


import com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter // Gera automaticamente os métodos "get" de todos os atributos
@Setter // Gera automaticamente os métodos "set" de todos os atributos
@AllArgsConstructor // Cria um construtor com todos os campos
@NoArgsConstructor  // Cria um construtor vazio
@Builder            // Permite construir objetos de forma mais legível e organizada
public class TarefasDTOResponse {

    private String id; // Identificador único da tarefa
    private String nomeTarefa; // Nome da tarefa cadastrada
    private String descricao; // Descrição detalhada da tarefa

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataCriacao; // Data e hora em que a tarefa foi criada (formato brasileiro)

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataEvento; // Data e hora previstas para o evento ou execução da tarefa

    private String emailUsuario; // E-mail do usuário dono da tarefa

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataAlteracao; // Data e hora da última atualização da tarefa

    private StatusNotificacaoEnum statusNotificacaoEnum; // Enum que indica o status da notificação (ex: PENDENTE, ENVIADA, FALHA)
}
