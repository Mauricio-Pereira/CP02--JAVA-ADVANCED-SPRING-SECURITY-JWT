# CP02 Project

## Integrantes
- Maurício Vieira Pereira - RM553748
- Yago Lucas Gonçalves - RM553013
  
## Descrição
Este projeto é uma API RESTful desenvolvida em Java utilizando Spring Boot. A API permite a gestão de cursos e diplomados, incluindo operações de CRUD (Create, Read, Update, Delete) e autenticação JWT.

## Tecnologias Utilizadas
- Java 21
- Spring Boot 3.3.4
- Spring Security
- Spring Data JPA
- H2 Database
- Gradle
- Postman

## Configuração do Projeto

### Pré-requisitos
- JDK 21
- Gradle
- Postman

### Configuração do Banco de Dados
O projeto utiliza o banco de dados H2 em memória para desenvolvimento e testes. A configuração do banco de dados pode ser encontrada no arquivo `application.properties`.  
O arquivo `application.properties` contém as configurações do banco de dados Oracle e caso necessário, altere as configurações de acordo com o banco de dados desejado.  
**OBS.:** Caso deseje utilizar o banco de dados Oracle, é necessário adicionar a dependência do driver Oracle no arquivo `build.gradle`.

### Variáveis de Ambiente
Certifique-se de definir a variável de ambiente `JWT_SECRET` com a chave secreta para geração de tokens JWT.  
Caso escolha utilizar o banco de dados Oracle, defina as variáveis de ambiente `dbUser` e `dbPassword` com as informações de conexão do banco de dados Oracle.

## Executando o Projeto
1. Clone o repositório:
   ```sh
   git clone https://github.com/Mauricio-Pereira/CP02--JAVA-ADVANCED-SPRING-SECURITY-JWT
   cd <NOME_DO_DIRETORIO>
   ```

2. Compile e execute o projeto:
   ```sh
   ./gradlew bootRun
   ```
   ou utilize o inicializador da IDE de sua preferência.  


3. Acesse a aplicação em `http://localhost:8080`.

## Endpoints da API

### Autenticação
- **POST /auth/login**: Autentica um usuário e retorna um token JWT(disponivel para qualquer role ).
- **POST /auth/register**: Registra um novo usuário(disponivel para qualquer role ).

### Cursos
- **GET /cursos**: Retorna todos os cursos (requer role `ADMIN`).
- **POST /cursos**: Cria um novo curso (requer role `ADMIN`).
- **GET /cursos/{id}**: Retorna um curso pelo ID(requer role `ADMIN`).
- **PUT /cursos/{id}**: Atualiza um curso pelo ID (requer role `ADMIN`).
- **DELETE /cursos/{id}**: Deleta um curso pelo ID (requer role `ADMIN`).

### Diplomados
- **GET /diplomas/all/{id}**: Retorna todos os diplomas de um usuário (disponivel para qualquer role ).
- **POST /diplomas**: Cria um novo diploma (requer role `ADMIN`).
- **GET /diplomas/{id}**: Retorna um diploma pelo ID(requer role `ADMIN`).
- **PUT /diplomas/{id}**: Atualiza um diploma pelo ID (requer role `ADMIN`).
- **DELETE /diplomas/{id}**: Deleta um diploma pelo ID (requer role `ADMIN`).


## Utilização do Postman
Para facilitar o uso da API, uma coleção do Postman (`postman/CP02.postman_collection.json`) está disponível no repositório.  
Importe a coleção no Postman e configure a variável `base_url` para `http://localhost:8080`.  
A coleção contém exemplos de requisições para todos os endpoints da API, porém é necessário autenticar um usuário e obter um token JWT para acessar os endpoints protegidos.  
Algumas alterações podem ser necessárias, como os id para os endpoints de PUT, DELETE e GET por ID.


## JWT Token
Para acessar os endpoints protegidos, é necessário incluir o token JWT no cabeçalho da requisição.   
O token JWT é gerado ao autenticar um usuário com sucesso no endpoint `POST /auth/login` e expira em 2 horas.
O token JWT deve ser incluído no cabeçalho da requisição com a chave `Authorization` e o valor `Bearer <TOKEN>`.  
