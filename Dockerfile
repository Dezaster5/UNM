# syntax=docker/dockerfile:1

FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /workspace

COPY pom.xml .
RUN mvn -B -DskipTests dependency:go-offline

COPY src ./src
RUN mvn -B clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine AS runtime
WORKDIR /app

RUN addgroup -S unm \
    && adduser -S unm -G unm \
    && mkdir -p /app/uploads /app/logs \
    && chown -R unm:unm /app

COPY --from=build /workspace/target/*.jar /app/app.jar

ENV SERVER_PORT=8080
ENV UPLOAD_DIR=/app/uploads
ENV LOG_PATH=/app/logs
ENV JAVA_OPTS=""

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=5s --start-period=40s --retries=3 \
    CMD wget -qO- "http://localhost:${SERVER_PORT}/api/health" >/dev/null || exit 1

USER unm

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
