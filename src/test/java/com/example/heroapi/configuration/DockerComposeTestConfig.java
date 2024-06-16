package com.example.heroapi.configuration;

import java.io.File;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PreDestroy;

@TestConfiguration
public class DockerComposeTestConfig {

    private static final int DB_PORT = 5432;
    private static DockerComposeContainer<?> composeContainer;

    static {
        // Start Docker Compose container before Spring context is initialized
        composeContainer = new DockerComposeContainer<>(new File("docker-compose.yml"))
            .withExposedService("db", DB_PORT, Wait.forListeningPort())
            .withStartupTimeout(java.time.Duration.ofMinutes(5));
        composeContainer.start();
    }

    @Bean
    public DockerComposeContainer<?> dockerComposeContainer() {
        return composeContainer;
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext context) {
            Dotenv dotenv = Dotenv.load();

            String dbHost = composeContainer.getServiceHost("db", DB_PORT);
            Integer dbPort = composeContainer.getServicePort("db", DB_PORT);
            String dbName = dotenv.get("DB_NAME");
            String dbUser = dotenv.get("DB_USER");
            String dbPassword = dotenv.get("DB_PASSWORD");

            String dbUrl = String.format("jdbc:postgresql://%s:%d/%s", dbHost, dbPort, dbName);

            TestPropertyValues.of(
                "spring.datasource.url=" + dbUrl,
                "spring.datasource.username=" + dbUser,
                "spring.datasource.password=" + dbPassword,
                "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect",
                "spring.jpa.properties.hibernate.default_schema=" + dbUser).applyTo(context.getEnvironment());
        }
    }

    @PreDestroy
    public void stopContainer() {
        if (composeContainer != null) {
            composeContainer.stop();
        }
    }
}