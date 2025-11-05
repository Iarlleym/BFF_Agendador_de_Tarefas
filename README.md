# 🌐 Microserviço: BFF (Backend For Frontend) - Agendador de Tarefas

Este microsserviço atua como o **BFF (Backend For Frontend)** para o frontend do sistema de agendamento de tarefas. Sua principal responsabilidade é **orquestrar** as operações que envolvem múltiplos microsserviços (Cadastro, Agendamento de Tarefas, Notificação), simplificando a interface para o cliente (frontend ou Postman).

O BFF centraliza a lógica de comunicação, segurança de serviço a serviço e agendamento de tarefas internas, agindo como um gateway inteligente para otimizar a experiência do usuário e a manutenção do sistema distribuído.

---

## 💻 Linguagem e Frameworks
<div style="display: inline_block"><br>
  <img align="center" alt="Java" height="30" width="40" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-plain.svg">
  <img align="center" alt="Spring Boot" height="30" width="40" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/spring/spring-original.svg">
  <img align="center" alt="Spring Security" height="30" width="40" src="https://img.shields.io/badge/Spring_Security-66BB6A?style=for-the-badge&logo=spring-security&logoColor=white">
  <img align="center" alt="OpenFeign" height="30" width="40" src="https://img.shields.io/badge/OpenFeign-007FFF?style=for-the-badge&logo=spring&logoColor=white">
  <img align="center" alt="Spring Data JPA" height="30" width="40" src="https://img.shields.io/badge/Spring_Data_JPA-66BB6A?style=for-the-badge&logo=spring&logoColor=white">
</div>

---

## 🔧 Funcionalidades Chave

-   **Orquestração de Chamadas:** Consolida requisições de múltiplos microsserviços em uma única resposta para o frontend.
    * **Cadastro de Usuário:** Consome o Microsserviço de Cadastro para login e registro.
    * **Gerenciamento de Tarefas:** Consome o Microsserviço de Agendamento para operações CRUD de tarefas.
-   **Segurança de Serviço-a-Serviço (via FeignClient):**
    * Utiliza **JWT** para autenticação e autorização das chamadas entre o BFF e os demais microsserviços.
-   **Tratamento Centralizado de Erros:**
    * Implementa um `ErrorDecoder` personalizado para o **FeignClient**, traduzindo erros HTTP (4xx, 5xx) dos microsserviços em exceções Java semânticas, proporcionando mensagens claras para o frontend.
-   **Agendamento de Tarefas Internas (CronService):**
    * Utiliza `@Scheduled` para executar tarefas em segundo plano (ex: a cada 2 minutos).
    * Realiza login de serviço no Microsserviço de Cadastro para obter um Token JWT.
    * Usa o Token obtido para buscar tarefas pendentes no Microsserviço de Agendamento.
    * Dispara notificações (e-mails) para o Microsserviço de Notificação com base nas tarefas agendadas.
-   **Proxy de API:** Atua como um proxy, protegendo os microsserviços internos da exposição direta ao cliente.

---

## 📂 Estrutura do Projeto

-   `controller`: Endpoints REST do BFF que o frontend consome.
-   `business`: Lógica de negócio do BFF, incluindo a orquestração e o `CronService`.
-   `infrastructure/client`: **Interfaces `FeignClient`** para cada microsserviço (Cadastro, Agendamento, Notificação).
    * `config`: Contém a configuração do `FeignClient`, incluindo o **`ErrorDecoder`** e a injeção do JWT.
-   `infrastructure/security`: Configuração do Spring Security para proteger as APIs do BFF e gerenciar a autenticação para chamadas de serviço-a-serviço.
-   `infrastructure/exceptions`: Definição das exceções personalizadas para o tratamento de erros.

---

## ⚙️ Configurações Essenciais

Para o correto funcionamento do microsserviço BFF, as seguintes variáveis de ambiente ou configurações no `application.properties`/`application.yml` são cruciais:

### URLs dos Microsserviços Consumidos (Feign Clients)

```properties
microsservico.cadastro.url=http://localhost:8080
microsservico.agendamento.url=http://localhost:8081
microsservico.notificacao.url=http://localhost:8082 # Exemplo de porta, ajuste conforme necessário
```

### Credenciais para Login de Serviço (CronService)

```properties
cron.service.username=seu_usuario_de_servico_para_cron
cron.service.password=sua_senha_de_servico_para_cron
```
*(Certifique-se de que este usuário existe e tem as permissões necessárias no Microsserviço de Cadastro.)*

### Configuração do Agendamento (CronService)

```properties
cron.tarefas.intervalo=120000 # Intervalo em milissegundos (ex: 120000ms = 2 minutos)
```

---

## 📌 Como Executar

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/SeuUsuario/NomeDoRepositorioBFF.git](https://github.com/SeuUsuario/NomeDoRepositorioBFF.git)
    cd NomeDoRepositorioBFF
    ```
2.  **Inicie os Microsserviços Dependentes:**
      * Garanta que os microsserviços de **Cadastro**, **Agendamento de Tarefas** e **Notificação** estejam rodando e acessíveis nas URLs configuradas (ex: `http://localhost:8080`, `http://localhost:8081`, `http://localhost:8082`).
3.  **Configure as Variáveis de Ambiente:**
      * Ajuste as URLs dos microsserviços (`microsservico.*.url`) conforme suas portas.
      * Configure as credenciais do `cron.service.username` e `cron.service.password`.
4.  **Execute a Aplicação:**
      * Você pode usar sua IDE (IntelliJ IDEA, Eclipse) ou via Maven:
        ```bash
        ./mvnw spring-boot:run
        ```

---

## 🚀 Acesso à Documentação da API (Swagger UI)

Após iniciar o serviço, você pode acessar a documentação interativa da API (OpenAPI/Swagger UI) no seu navegador:

[http://localhost:8083/swagger-ui.html](http://localhost:8083/swagger-ui.html)

**(Nota: Assumindo que o serviço BFF está rodando na porta 8083, ou a porta que você configurou.)**
