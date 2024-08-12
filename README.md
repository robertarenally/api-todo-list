# Gestão de Tarefas API

Esta é uma API para gerenciamento de tarefas construída com Spring Boot, MongoDB, Redis e Docker. A API permite criar, ler, atualizar e deletar tarefas, além de buscar tarefas por status. A autenticação é gerida por OAuth2 e a documentação da API é gerada pelo Swagger.

## Pré-requisitos

Antes de começar, certifique-se de ter os seguintes softwares instalados:

- [Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven](https://maven.apache.org/)
- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/)

## Configuração

### Configuração Local

1. Clone o repositório:
   ```
   git clone https://github.com/seu-usuario/seu-repositorio.git
   cd seu-repositorio
   ```
2. Configure as conexões com o MongoDB, Redis, Amozon S3 no arquivo api-todo-list/src/main/resources/application.properties:
   ```
   # Configuração do MongoDB
   spring.data.mongodb.uri=mongodb://localhost:27017/tarefas

   # Configurações de OAuth2 com Google
   spring.security.oauth2.client.registration.google.client-id=your-client-id
   spring.security.oauth2.client.registration.google.client-secret=your-client-secret
   spring.security.oauth2.client.registration.google.scope=profile,email
   spring.security.oauth2.client.registration.google.redirect-uri={baseUrl}/login/oauth2/code/{registrationId}

   # Configurações do Redis
   spring.redis.host=localhost
   spring.redis.port=6379

   # Configurações do AWS S3
   aws.s3.access-key-id=your-access-key-id
   aws.s3.secret-access-key=your-secret-access-key
   aws.s3.region=your-region
   aws.s3.bucket-name=your-bucket-name
   ```

### Executar a Aplicação com Docker

1. Construir e iniciar os containers Docker:
No diretório onde estão Dockerfile e docker-compose.yml, execut:
   ```
   docker-compose up --build
   ```
Esse comando realiza as seguintes ações:

 - Constrói a imagem da aplicação a partir do Dockerfile.
 - Inicia os containers para a aplicação, MongoDB e Redis.
2. Acessar a API:
 - A aplicação estará disponível em http://localhost:8080.
3. Acessar a documentação Swagger:
 - A documentação da API gerada pelo Swagger pode ser acessada em http://localhost:8080/swagger-ui.html.
4. Para parar e remover os containers, redes e volumes criados pelo Docker Compose, use:
   ```
   docker-compose down
   ```

### Executar Testes Unitários

Para executar os testes unitários da aplicação, siga os passos abaixo:

1. Certifique-se de que o Maven está instalado.
2. Execute os testes com o comando: 
   ```
   mvn test
   ```