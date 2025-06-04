# Usa uma imagem oficial do Maven e JDK 17 (Eclipse Temurin é uma boa distro)
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Define o diretório de trabalho dentro do contêiner para o estágio de build
WORKDIR /app

# Se o pom.xml não mudar, esta camada será cacheada, evitando redownload de dependências.
COPY pom.xml .

# Baixa as dependências.
RUN mvn dependency:go-offline -B

# Copia todo o código fonte da aplicação para o diretório de trabalho
COPY src ./src

# Compila o projeto e empacota a aplicação em um JAR executável.
RUN mvn package -DskipTests

# 'eclipse-temurin:17-jre-focal' é o recomendado para JRE 17.
FROM eclipse-temurin:17-jre-focal

# Define o diretório de trabalho para a aplicação em execução
WORKDIR /app

# Copia o JAR executável do estágio de build para o estágio final.
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta que a aplicação Spring Boot irá escutar (padrão é 8080)
EXPOSE 8080

# Comando para rodar a aplicação Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]