# Build the Spring Boot app
RUN ./mvnw clean package -DskipTests

# Copy the JAR to a fixed name
RUN cp target/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the jar file
CMD ["java", "-jar", "app.jar"]
