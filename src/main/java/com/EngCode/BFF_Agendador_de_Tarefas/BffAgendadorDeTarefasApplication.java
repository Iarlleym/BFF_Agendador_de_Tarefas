package com.EngCode.BFF_Agendador_de_Tarefas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class BffAgendadorDeTarefasApplication {

	public static void main(String[] args) {
		SpringApplication.run(BffAgendadorDeTarefasApplication.class, args);
	}

}
