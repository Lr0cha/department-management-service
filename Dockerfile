FROM amazoncorretto:21

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot JAR file into the container
COPY target/UserDept-0.0.1-SNAPSHOT.jar /app/UserDept-0.0.1-SNAPSHOT.jar

EXPOSE 8080

# Command to run your Spring Boot application
CMD ["java", "-jar", "/app/UserDept-0.0.1-SNAPSHOT.jar"]