# ── Stage 1: Build React frontend ─────────────────────────────────────────────
FROM node:22-alpine AS frontend-build
WORKDIR /app
COPY react/my-app/package*.json ./
RUN npm ci
COPY react/my-app/ ./
RUN npm run build
# Output lands in /app/dist

# ── Stage 2: Build Spring Boot backend ────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-17 AS backend-build
WORKDIR /app

# Cache Maven dependencies before copying source
COPY spring-api/pom.xml .
RUN mvn dependency:go-offline -q

COPY spring-api/src ./src

# Overwrite resources/static with the freshly built React output
COPY --from=frontend-build /app/dist ./src/main/resources/static

RUN mvn package -DskipTests -q

# ── Stage 3: Runtime ──────────────────────────────────────────────────────────
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=backend-build /app/target/tippmix-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
