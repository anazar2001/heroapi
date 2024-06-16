FROM openjdk:22-jdk-slim

ENV APP_HOME=/usr/heroapi

# Create base app directory
WORKDIR $APP_HOME

COPY target/heroapi-0.0.1-SNAPSHOT.jar /usr/heroapi/heroapi.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/heroapi/heroapi.jar"]