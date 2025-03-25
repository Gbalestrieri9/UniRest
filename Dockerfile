# Escolhe a imagem base do OpenJDK 17 (pode variar)
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o JAR gerado pelo Maven para dentro do container
# Ajuste o nome do JAR de acordo com o que seu build gerar
COPY target/UniRest-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta 8080 (opcional, pois o docker-compose também faz esse mapeamento)
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
