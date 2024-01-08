# Start with a base image containing Java runtime (Java 17 in this case)
FROM openjdk:17

WORKDIR /app
# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file (adjust the jar file name as per your project)
COPY target/tictac-0.0.1-SNAPSHOT.jar /app/tictac.jar

# Run the jar file
CMD ["java", "-jar", "tictac.jar"]