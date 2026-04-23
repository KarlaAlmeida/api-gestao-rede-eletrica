# Gestão de Manutenção da Rede Elétrica

Este projeto é um sistema robusto e escalável para a gestão de manutenção da rede elétrica. Ele fornece uma maneira estruturada de registrar e gerenciar ativos, técnicos, ocorrências e ordens de serviço, estabelecendo a base para uma plataforma completa de gerenciamento de manutenção.

## Objetivos

O objetivo principal é estruturar um sistema para o registro de ativos, técnicos, ocorrências e ordens de serviço para gerenciar a manutenção da rede elétrica.

### Objetivos Específicos

- **Cadastro de Ativos, Técnicos, Ocorrências e Ordens de Serviço:** Criar uma estrutura clara e organizada para o registro dessas entidades centrais.
- **Associações Claras:** Associar ativos a ocorrências e vinculá-los a técnicos e ordens de serviço.
- **Recuperação Eficiente de Informações:** Facilitar a busca e a recuperação de informações.
- **Autenticação e controle de acesso baseado em papéis:** Garantir que apenas usuários autorizados acessem e modifiquem os dados do sistema.
- **Base para Funcionalidades Futuras:** Estabelecer uma base confiável para futuras melhorias, incluindo:
  - Rastreamento do histórico de ativos.
  - Integração em tempo real com o atendimento ao cliente ou sistemas de monitoramento.
  - Planejamento automatizado de manutenção preventiva.
  - Relatórios e indicadores de desempenho.

## Tecnologias e Ferramentas

Este projeto foi construído com uma pilha de tecnologia moderna e robusta:

- **Java 21:** A versão mais recente de suporte a longo prazo (LTS) do Java.
- **Spring Boot 3:** Para criar aplicações Spring autônomas e de nível de produção.
- **Spring Web:** Para construir APIs RESTful.
- **Spring Data JPA:** Para persistir dados em um banco de dados SQL.
- **PostgreSQL:** Banco de dados relacional robusto para produção e desenvolvimento.
- **Spring Security & JWT:** Para autenticação e autorização seguras baseadas em tokens.
- **OpenFeign:** Para consumo de APIs externas (ViaCEP e OpenStreetMap).
- **Lombok:** Reduz o código repetitivo para modelos e objetos de log.
- **Maven:** Uma poderosa ferramenta de gerenciamento e compreensão de projetos.
- **Docker & Docker Compose:** Para containerização e facilidade de deploy.

## Estrutura do Projeto

O projeto segue uma arquitetura clássica em camadas, promovendo a separação de responsabilidades e a manutenibilidade:

- **`auth`:** Camada de segurança contendo configurações do Spring Security, serviços JWT e controladores de login.
- **`controller`:** Contém os controladores REST que expõem os endpoints da API.
- **`model/domain/entities`:** As entidades JPA que representam os objetos de domínio principais.
- **`model/repository`:** Os repositórios Spring Data JPA para acesso a dados.
- **`model/service`:** A camada de lógica de negócios da aplicação.
- **`model/loader`:** Classes responsáveis por carregar dados iniciais no banco de dados na inicialização.
- **`model/clients`:** Clientes Feign para integração com serviços externos.

## Como Executar Localmente

### Pré-requisitos
- Java 21 JDK
- Maven 3.x
- PostgreSQL (opcional, se não usar Docker)

### Passos
1. **Clone o repositório:**
   ```bash
   git clone <url-do-repositorio>
   cd karlaapi
   ```

2. **Configuração de Banco de Dados:**
   A aplicação está configurada para se conectar ao PostgreSQL. Você pode configurar as seguintes variáveis de ambiente ou usar os valores padrão em `src/main/resources/application.properties`:
   - `DB_HOST`: Host do banco (padrão: `localhost`)
   - `DB_PORT`: Porta do banco (padrão: `5432`)
   - `DB_NAME`: Nome do banco (padrão: `db_rede_eletrica`)
   - `DB_USER`: Usuário (padrão: `postgres`)
   - `DB_PASSWORD`: Senha (padrão: `141724Ka!`)

3. **Execute a aplicação:**
    ```bash
    ./mvnw spring-boot:run
    ```
    A API estará disponível em `http://localhost:8081`.

## Instruções de Deploy (Docker)

Para executar o ambiente containerizado localmente:

1. **Certifique-se de ter o Docker e Docker Compose instalados.**
2. **Execute o comando:**
   ```bash
   docker-compose up --build
   ```
   Este comando irá subir dois containers:
   - `postgres-db`: Banco de dados PostgreSQL na porta 5432.
   - `karla-api`: A aplicação Spring Boot na porta 8081.

A aplicação aguarda o banco de dados estar pronto (healthcheck) antes de iniciar.

## Endpoints da API

### Autenticação
- `POST /api/auth/login`: Autenticação de usuário e geração de token JWT.

### Ativos
- `POST /api/ativos`: Cria um novo ativo.
- `GET /api/ativos`: Recupera todos os ativos (paginado).
- `GET /api/ativos/{id}`: Recupera um ativo específico.
- `PUT /api/ativos/{id}`: Atualiza um ativo.
- `DELETE /api/ativos/{id}`: Deleta um ativo.

### Técnicos
- `POST /api/tecnicos`: Cria um novo técnico.
- `GET /api/tecnicos`: Recupera todos os técnicos (paginado).
- `GET /api/tecnicos/{id}`: Recupera um técnico específico.
- `PUT /api/tecnicos/{id}`: Atualiza um técnico.
- `PATCH /api/tecnicos/{id}/status`: Altera status ativo/inativo.
- `PATCH /api/tecnicos/{id}/disponibilidade`: Altera disponibilidade.
- `GET /api/tecnicos/filtro/nome-e-especialidade`: Busca filtrada.
- `DELETE /api/tecnicos/{id}`: Deleta um técnico.

### Ocorrências
- `POST /api/ocorrencias`: Cria uma nova ocorrência.
- `GET /api/ocorrencias`: Recupera todas as ocorrências (paginado).
- `GET /api/ocorrencias/{id}`: Recupera uma ocorrência específica.
- `PUT /api/ocorrencias/{id}`: Atualiza uma ocorrência.
- `DELETE /api/ocorrencias/{id}`: Deleta uma ocorrência.

### Ordens de Serviço
- `POST /api/ordem-servico`: Cria uma nova ordem de serviço.
- `GET /api/ordem-servico`: Recupera todas as ordens de serviço (paginado).
- `GET /api/ordem-servico/{id}`: Recupera uma OS específica.
- `PUT /api/ordem-servico/{id}`: Atualiza uma OS.
- `PATCH /api/ordem-servico/{id}/status`: Altera status da OS.
- `GET /api/ordem-servico/tecnico/{cpf}`: Lista OS por técnico.
- `DELETE /api/ordem-servico/{id}`: Deleta uma OS.

### Dashboard e Outros
- `GET /api/dashboard`: Dados consolidados para o dashboard.
- `GET /api/enderecos/{cep}`: Consulta georreferenciada por CEP.

## Funcionalidades Implementadas

| Funcionalidade | Descrição | Localização do Código |
| :--- | :--- | :--- |
| **Autenticação JWT** | Login e geração de token seguro. | [LoginController.java](src/main/java/br/edu/infnet/karlaapi/auth/controller/LoginController.java) |
| **Segurança** | Configuração de filtros e permissões. | [SecurityConfig.java](src/main/java/br/edu/infnet/karlaapi/auth/config/SecurityConfig.java) |
| **Gestão de Ativos** | CRUD completo de ativos da rede. | [AtivoController.java](src/main/java/br/edu/infnet/karlaapi/controller/AtivoController.java) |
| **Gestão de Técnicos** | Cadastro e controle de disponibilidade. | [TecnicoController.java](src/main/java/br/edu/infnet/karlaapi/controller/TecnicoController.java) |
| **Ordens de Serviço** | Fluxo completo de manutenção. | [OrdemServicoController.java](src/main/java/br/edu/infnet/karlaapi/controller/OrdemServicoController.java) |
| **Geolocalização** | Integração com ViaCEP e OpenStreetMap. | [GeolocalizacaoFeignClient.java](src/main/java/br/edu/infnet/karlaapi/model/clients/GeolocalizacaoFeignClient.java) |
| **Dashboard** | Indicadores gerais do sistema. | [DashboardService.java](src/main/java/br/edu/infnet/karlaapi/model/service/DashboardService.java) |

### Como Testar
1. Utilize o `POST /api/auth/login` com as credenciais iniciais (carregadas via `DataLoader` se configurado) para obter o token.
2. Inclua o token no header das requisições subsequentes: `Authorization: Bearer <seu_token>`.
3. Utilize ferramentas como Postman ou Insomnia para interagir com os endpoints listados acima.

------------------------------
