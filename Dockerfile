FROM openjdk:17-jdk

# Add the application's jar to the container
COPY target/user-profile-service-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 and run the application
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]