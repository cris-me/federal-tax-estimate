# Use an official Java runtime as the base image
FROM openjdk:8

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the host into the container
COPY target/*.jar app.jar

# Command to run when the container starts
CMD ["java", "-jar", "app.jar"]
