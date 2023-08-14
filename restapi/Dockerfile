# Stage 1: Build the JAR
FROM gradle:7.2.0-jdk11 AS builder
WORKDIR /app
COPY build.gradle .
COPY src ./src
RUN gradle build -x test


# Stage 2: Create a lightweight image
FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]