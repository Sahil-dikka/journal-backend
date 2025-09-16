# Stage 1: Build the Spring Boot app
FROM maven:3.9.2-eclipse-temurin-17 AS build
WORKDIR /app

# Copy Maven wrapper and project files
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
COPY src ./src

# Package the app (skip tests to speed up)
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the Spring Boot app
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
