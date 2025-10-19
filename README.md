# SOASprint: API de Gestão de Profil e Carteira de Investimentos

## 🚀 Descrição do Projeto
O **SOASprint** é uma API REST desenvolvida com **Java** e **Spring Boot**, focada em uma **Arquitetura Orientada a Serviços (SOA)**. A aplicação permite o gerenciamento de perfis de investidores e suas respectivas carteiras financeiras, com endpoints seguros e bem definidos para criação, consulta, atualização e exclusão de dados.

O projeto implementa autenticação via **JWT (JSON Web Token)**, garantindo que apenas usuários autorizados possam acessar e manipular seus dados. A documentação da API é gerada dinamicamente com **Swagger/OpenAPI**, facilitando os testes e a integração.

---

**Integrantes do Grupo:**
* Eduardo Araujo (RM99758)
* Gabriela Trevisan (RM99500)
* Leonardo Bonini (RM551716)
* Rafael Franck (RM550875)

---

## 🛠️ Tecnologias Utilizadas
* **Linguagem:** Java 21
* **Framework:** Spring Boot 3.2.5
* **Segurança:** Spring Security, JSON Web Token (JWT)
* **Banco de Dados:** Oracle SQL
* **Migrações:** Flyway
* **Documentação:** Swagger / OpenAPI
* **Build & Dependências:** Maven
* **Controle de Versão:** Git

---

## 🏛️ Arquitetura

#### Diagrama de Camadas
O projeto é estruturado em camadas para garantir a separação de responsabilidades e facilitar a manutenção:
* **Controller:** Responsável por receber as requisições HTTP, validar os dados de entrada e roteá-las para a camada de serviço.
* **Service:** Contém toda a lógica de negócio da aplicação.
* **Repository:** Camada de acesso a dados (Data Access Layer), responsável pela comunicação com o banco de dados via Spring Data JPA.
* **Domain/Model:** Representação das entidades do banco de dados (`Usuario`, `Perfil`, `Carteira`).
* **DTO (Data Transfer Object):** Objetos utilizados para transferir dados entre as camadas, garantindo que a API exponha apenas as informações necessárias.
* **Security:** Camada responsável pela configuração de autenticação e autorização via JWT.

![Diagrama de Arquitetura](Diagrama.png)

#### Modelo de Entidades
O modelo de dados é composto por três entidades principais:

* **`USUARIO`**: Armazena as credenciais para autenticação.
    * `ID` (PK): Identificador único.
    * `EMAIL` (unique): E-mail usado para login.
    * `PASSWORD`: Senha criptografada.
* **`PERFIL`**: Armazena as informações do perfil do investidor.
    * `ID` (PK): Identificador único.
    * `NOME`: Nome do usuário.
    * `EMAIL`: E-mail de contato.
    * `OBJETIVO_FINANCEIRO`, `TOLERANCIA_RISCO`, etc.
* **`CARTEIRA`**: Armazena os detalhes da carteira de investimentos, vinculada a um `Perfil`.
    * `ID` (PK): Identificador único.
    * `PERFIL_ID` (FK): Chave estrangeira que referencia a tabela `PERFIL`.
    * `NOME`, `VALOR_TOTAL`, `ESTRATEGIA`, `ATIVOS`.

---

## ⚙️ Configuração e Execução

**1. Pré-requisitos:**
* **Java (JDK) 21** ou superior.
* **Maven** 3.8 ou superior.
* Acesso a um banco de dados **Oracle**.

**2. Clone o repositório:**

    git clone https://github.com/LeoViBo/SOASprint.git
    cd SOASprint

**3. Configure o Banco de Dados:**
* Abra o arquivo `src/main/resources/application.properties`.
* Atualize as propriedades `spring.datasource.url`, `spring.datasource.username` e `spring.datasource.password` com as credenciais do seu banco Oracle.
```
spring.datasource.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
```

**4. Execute a Aplicação:**
* Abra um terminal na raiz do projeto e execute o comando:

    `mvn spring-boot:run`

* A aplicação será iniciada e estará disponível na porta `8080`.

---

## 🧪 Como Testar a API com Swagger

A forma mais fácil de testar os endpoints é utilizando a interface do Swagger, que documenta toda a API de forma interativa.

**1. Acesse a documentação:**
* Com a aplicação rodando, abra o seu navegador e acesse a URL:
    [**http://localhost:8080/swagger-ui.html**](http://localhost:8080/swagger-ui.html)

**2. Crie um novo Perfil e Usuário:**
* No Swagger, encontre o endpoint `POST /perfis`.
* Clique em "Try it out" e preencha o "Request body" com os dados do novo usuário. A senha é obrigatória para criar as credenciais de login.
```
    {
      "nome": "Gabriela Trevisan",
      "email": "gabi.trevisan@example.com",
      "password": "senhaForte123",
      "objetivoFinanceiro": "Aposentadoria",
      "toleranciaRisco": "Moderado",
      "valorParaInvestimento": 5000.00,
      "horizonteDeTempo": 10
    }
```
* Clique em "Execute". Você deve receber uma resposta com status `201 Created`.

**3. Autentique-se para obter um Token:**
* Encontre o endpoint `POST /auth/login`.
* Preencha o corpo da requisição com o **mesmo e-mail e senha** que você acabou de cadastrar:
```
    {
      "email": "gabi.trevisan@example.com",
      "password": "senhaForte123"
    }
```
* Execute a requisição. A resposta será um JSON contendo o seu token de acesso (JWT). **Copie o valor do token.**

**4. Autorize suas requisições:**
* No topo da página do Swagger, clique no botão **"Authorize"**.
* Na janela que abrir, cole o token que você copiou no campo "Value", prefixado por `Bearer ` (com um espaço no final).
    * **Exemplo:** `Bearer eyJhbGciOiJIUzI1NiJ9...`
* Clique em "Authorize" e feche a janela. A partir de agora, todas as suas requisições aos endpoints protegidos serão autenticadas.

**5. Teste outros endpoints:**
* Agora você pode testar endpoints que exigem autenticação, como `POST /carteiras` ou `GET /perfis`. O token será enviado automaticamente.
