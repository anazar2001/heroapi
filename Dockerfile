FROM openjdk:22-jdk-slim

ENV APP_HOME=/usr/heroapi

# Create base app directory
WORKDIR $APP_HOME

COPY target/heroapi-0.0.1-SNAPSHOT.jar /usr/heroapi/heroapi.jar

# Copy the initialization script to the Docker container
# COPY src/main/resources/initdb /docker-entrypoint-initdb.d

EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/heroapi/heroapi.jar"]