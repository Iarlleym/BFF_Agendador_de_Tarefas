package com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.enums;

/**
 * 🔹 Enum que representa os possíveis status de notificação de uma tarefa.
 *
 * Um enum (abreviação de "enumeration") é um tipo especial no Java usado
 * para representar um conjunto fixo de constantes — valores que não mudam.
 *
 * Nesse caso, cada valor indica em qual estado a notificação da tarefa se encontra.
 */
public enum StatusNotificacaoEnum {

    PENDENTE,     // A tarefa ainda não foi notificada (aguardando envio de alerta ou lembrete).
    NOTIFICADO,   // A tarefa já foi notificada (o alerta foi enviado com sucesso).
    CANCELADO     // A notificação foi cancelada (não será mais enviada).
}
