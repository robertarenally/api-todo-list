# Etapa de construção
FROM maven:3.8.6-openjdk-11 AS build

# Diretório de trabalho
WORKDIR /app

# Copiar o pom.xml e baixar as dependências
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar o código-fonte e compilar a aplicação sem executar os testes unitários
COPY src ./src
RUN mvn package -DskipTests

# Etapa de execução
FROM tomcat:9.0-jdk11-openjdk-slim

# Diretório de trabalho
WORKDIR /usr/local/tomcat/webapps

# Copiar o WAR construído da etapa anterior para o diretório de deploy do Tomcat
COPY --from=build /app/target/*.war api-todo-list.war

# Expor a porta em que o Tomcat será executado
EXPOSE 8080