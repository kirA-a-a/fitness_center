# Build stage
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY src src

RUN chmod +x gradlew && ./gradlew bootJar --no-daemon -x test

# Run stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

RUN adduser -D appuser && chown -R appuser /app
USER appuser

COPY --from=build /app/build/libs/*.jar app.jar

# Render передаёт PORT в env
ENV SERVER_PORT=8080
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar app.jar"]
