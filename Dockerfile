# Use an official JDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy Maven/Gradle build files and source code
COPY . .

# Build the application (Maven here — adjust if you use Gradle)
RUN ./mvnw clean package -DskipTests

# Run the Spring Boot application
CMD ["java", "-jar", "target/*.jar"]
