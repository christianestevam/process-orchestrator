# Usando a imagem oficial do OpenJDK 21 para rodar a aplicação
FROM eclipse-temurin:21-jdk-alpine AS build

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo pom.xml e instala dependências sem compilar o código
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

# Copia o restante do código-fonte para o container
COPY src src

# Compila a aplicação e gera o arquivo JAR
RUN ./mvnw clean package -DskipTests

# Criando a imagem final para rodar o Spring Boot
FROM eclipse-temurin:21-jdk-alpine

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o JAR gerado da etapa anterior
COPY --from=build /app/target/process-orchestrator-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta 8080 para o container
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
