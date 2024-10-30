# JDK image
FROM openjdk:21-jdk-slim

# working directory
WORKDIR /app

# copy jar file to app container
COPY target/spaceship-0.0.1-SNAPSHOT.jar app.jar

# config application port
EXPOSE 8080

# execute app command
ENTRYPOINT [ "java", "-jar", "app.jar" ]

# generate docker container with:
#   docker build -t spaceship-app <path/Dockerfile>
