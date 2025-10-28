package com.EngCode.BFF_Agendador_de_Tarefas.business;

// BLOCÃO 1: IMPORTAÇÕES E FERRAMENTAS
// -------------------------------------------------------------------------

// DTOs de comunicação e resposta
import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.in.LoginDTORequest;
import com.EngCode.BFF_Agendador_de_Tarefas.business.dto.out.TarefasDTOResponse;
import com.EngCode.BFF_Agendador_de_Tarefas.infrastructure.enums.StatusNotificacaoEnum; // Enum de status da notificação

// Lombok e Spring
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // Anotação para Logs
import org.springframework.beans.factory.annotation.Value; // Para puxar variáveis de configuração
import org.springframework.scheduling.annotation.Scheduled; // Anotação principal para agendamento
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// BLOCÃO 2: ESTRUTURA E INJEÇÃO DE DEPENDÊNCIA
// -------------------------------------------------------------------------
@Slf4j // LOMBOK: Cria o logger (objeto 'log') para registrar informações no console.
@Service // ANOTAÇÃO SPRING: Marca a classe como um Componente de Serviço.
@RequiredArgsConstructor // LOMBOK: Gera o construtor para injeção das dependências 'final'.
public class CronService {

    // Dependências de outros Services (Orquestração Inter-Serviços)
    private final TarefasService tarefasService; // Para buscar e atualizar tarefas (via FeignClient).
    private final EmailService emailService; // Para disparar o e-mail (via FeignClient).
    private final UsuarioService usuarioService; // Para realizar o login e obter o Token JWT.

    // INJEÇÃO DE VALORES: Credenciais de um 'usuário de serviço' para obter o Token JWT.
    @Value("${usuario.email}")
    private  String email;
    @Value("${usuario.senha}")
    private  String senha;

    // BLOCÃO 3: MÉTODO AGENDADO
    // -------------------------------------------------------------------------

    // ANOTAÇÃO AGENDAMENTO: O Spring executará este método periodicamente.
    // O valor é puxado da variável 'cron.horario' (ex: "0 */2 * * * *", para cada 2 minutos).
    @Scheduled(cron = "${cron.horario}")

    // O @Slf4j estava aqui, mas ele deve ficar no nível da classe. Foi corrigido.
    public void buscarTarefasDaProximaHora () {

        // 1. AUTENTICAÇÃO: Realiza o login usando as credenciais injetadas.
        String token = login(converterParaDTORequest());

        // LOG MELHORADO: Inicia o processo.
        log.info(">>> Iniciando o ciclo agendado de busca e notificação de Tarefas.");

        // 2. CÁLCULO DE TEMPO: Define o período de busca (agora até a próxima hora).
        LocalDateTime horaAtual = LocalDateTime.now();
        LocalDateTime horaFutura = LocalDateTime.now().plusHours(1);

        // 3. BUSCA DE DADOS: Chama o TarefasService para buscar as tarefas do período, repassando o Token.
        List<TarefasDTOResponse> listaDeTarefas = tarefasService.buscaTarefasAgendadasPorPeriodo(horaAtual, horaFutura, token);

        // LOG MELHORADO: Informa quantas tarefas foram encontradas.
        log.info("Encontradas {} tarefas agendadas para notificação na próxima hora.", listaDeTarefas.size());

        // 4. PROCESSAMENTO E NOTIFICAÇÃO (Loop)
        listaDeTarefas.forEach(tarefa -> {

            emailService.enviaEmail(tarefa); // Envia o e-mail para a tarefa atual.

            // LOG MELHORADO: Registra o sucesso do envio para rastreabilidade.
            log.info("E-mail de notificação disparado com sucesso para o usuário: {}.", tarefa.getEmailUsuario());

            // Altera o status da tarefa para evitar que o e-mail seja enviado novamente no próximo ciclo.
            // O Microsserviço de Agendamento lida com a lógica de PATCH.
            tarefasService.alteraStatusDaTarefa(StatusNotificacaoEnum.NOTIFICADO, tarefa.getId(), token);

        });

        // LOG MELHORADO: Finaliza o processo.
        log.info("<<< Finalizado o ciclo agendado de notificação. Tarefas processadas: {}.", listaDeTarefas.size());
    }

    /**
     * MÉTODO: login(LoginDTORequest)
     * FUNÇÃO: Executa o login no Microsserviço de Usuário.
     * CONCEITO: Pass-through (Passagem direta) para o UsuarioService.
     */
    public String login (LoginDTORequest loginDTORequest){

        return usuarioService.loginUsuario(loginDTORequest);
    }

    /**
     * MÉTODO: converterParaDTORequest()
     * FUNÇÃO: Converte as variáveis @Value (e-mail e senha) em um DTO de Requisição.
     * CONCEITO: Criação do Objeto de Transferência de Dados para o login.
     */
    public LoginDTORequest converterParaDTORequest () {
        return LoginDTORequest.builder()
                .email(email)
                .senha(senha)
                .build();
    }

}