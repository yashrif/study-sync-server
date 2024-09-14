# First stage: build the application
FROM maven:3.9.8-eclipse-temurin-21 AS build
COPY . /app
WORKDIR /app
RUN mvn package -DskipTests

# Second stage: create a slim image
FROM openjdk:21-jdk-slim
EXPOSE 8080
COPY --from=build /app/target/studysync-0.0.1-SNAPSHOT.jar /app.jar
