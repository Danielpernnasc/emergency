FROM eclipse-temurin:21-jdk-alpine

# cria usuário
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

COPY target/emergencyroom-0.0.1-SNAPSHOT.jar app.jar

# usa usuário seguro
USER appuser

ENTRYPOINT ["java", "-jar", "app.jar"]
