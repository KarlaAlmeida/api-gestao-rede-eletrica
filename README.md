# Gestão de Manutenção da Rede Elétrica

Este projeto é um sistema robusto e escalável para a gestão de manutenção da rede elétrica. Ele fornece uma maneira estruturada de registrar e gerenciar ativos, técnicos, ocorrências e ordens de serviço, estabelecendo a base para uma plataforma completa de gerenciamento de manutenção.

## Objetivos

O objetivo principal é estruturar um sistema para o registro de ativos, técnicos, ocorrências e ordens de serviço para gerenciar a manutenção da rede elétrica.

### Objetivos Específicos

- **Cadastro de Ativos, Técnicos, Ocorrências e Ordens de Serviço:** Criar uma estrutura clara e organizada para o registro dessas entidades centrais.
- **Associações Claras:** Associar ativos a ocorrências e vinculá-los a técnicos e ordens de serviço.
- **Recuperação Eficiente de Informações:** Facilitar a busca e a recuperação de informações.
- **Base para Funcionalidades Futuras:** Estabelecer uma base confiável para futuras melhorias, incluindo:
  - Rastreamento do histórico de ativos.
  - Integração em tempo real com o atendimento ao cliente ou sistemas de monitoramento.
  - Planejamento automatizado de manutenção preventiva.
  - Relatórios e indicadores de desempenho.
  - Autenticação e controle de acesso baseado em funções.

## Tecnologias e Ferramentas

Este projeto foi construído com uma pilha de tecnologia moderna e robusta:

- **Java 21:** A versão mais recente de suporte a longo prazo (LTS) do Java.
- **Spring Boot 3:** Para criar aplicações Spring autônomas e de nível de produção.
- **Spring Web:** Para construir APIs RESTful.
- **Spring Data JPA:** Para persistir dados em um banco de dados SQL com a Java Persistence API usando Spring Data e Hibernate.
- **H2 Database:** Um banco de dados em memória, ideal para desenvolvimento e testes.
- **Lombok:** Reduz o código repetitivo para modelos e objetos de log.
- **Maven:** Uma poderosa ferramenta de gerenciamento e compreensão de projetos.

## Estrutura do Projeto

O projeto segue uma arquitetura clássica em camadas, promovendo a separação de responsabilidades e a manutenibilidade:

- **`controller`:** Contém os controladores REST que expõem os endpoints da API.
- **`model/domain/entities`:** As entidades JPA que representam os objetos de domínio principais.
- **`model/repository`:** Os repositórios Spring Data JPA para acesso a dados.
- **`model/service`:** A camada de lógica de negócios da aplicação.
- **`model/loader`:** Classes responsáveis por carregar dados iniciais no banco de dados na inicialização.

## Como Executar

1. **Clone o repositório:**
   ```bash
   git clone <url-do-repositorio>
   ```
2. **Navegue até o diretório do projeto:**
    ```bash
    cd karlaapi
    ```
3. **Execute a aplicação usando o Maven:**
    ```bash
    ./mvnw spring-boot:run
    ```
4. **Acesse o Console H2:**
    - A aplicação será iniciada na porta `8080`.
    - Abra seu navegador e acesse `http://localhost:8080/h2-console`.
    - Use as seguintes configurações para conectar:
        - **Driver Class:** `org.h2.Driver`
        - **JDBC URL:** `jdbc:h2:~/databaseKarla`
        - **User Name:** `sa`
        - **Password:** (deixe em branco)

## Endpoints da API

A aplicação fornece os seguintes endpoints REST para gerenciar as entidades principais:

- `POST /api/ativos`: Cria um novo ativo.
- `GET /api/ativos`: Recupera todos os ativos.
- `GET /api/ativos/{id}`: Recupera um ativo específico.
- `PUT /api/ativos/{id}`: Atualiza um ativo.
- `DELETE /api/ativos/{id}`: Deleta um ativo.

- `POST /api/tecnicos`: Cria um novo técnico.
- `GET /api/tecnicos`: Recupera todos os técnicos.
- `GET /api/tecnicos/{id}`: Recupera um técnico específico.
- `PUT /api/tecnicos/{id}`: Atualiza um técnico.
- `DELETE /api/tecnicos/{id}`: Deleta um técnico.

- `POST /api/ocorrencias`: Cria uma nova ocorrência.
- `GET /api/ocorrencias`: Recupera todas as ocorrências.
- `GET /api/ocorrencias/{id}`: Recupera uma ocorrência específica.
- `PUT /api/ocorrencias/{id}`: Atualiza uma ocorrência.
- `DELETE /api/ocorrencias/{id}`: Deleta uma ocorrência.

- `POST /api/ordens-servico`: Cria uma nova ordem de serviço.
- `GET /api/ordens-servico`: Recupera todas as ordens de serviço.
- `GET /api/ordens-servico/{id}`: Recupera uma ordem de serviço específica.
- `PUT /api/ordens-servico/{id}`: Atualiza uma ordem de serviço.
- `DELETE /api/ordens-servico/{id}`: Deleta uma ordem de serviço.

## Funcionalidades Futuras

- Registro de histórico de ativos.
- Integração em tempo real com chamadas de clientes ou sistemas de monitoramento.
- Planejamento automatizado de manutenção preventiva.
- Relatórios e indicadores de desempenho.
- Autenticação e controle de acesso baseado em papéis.
