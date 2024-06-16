package com.example.heroapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration.class})
public class HeroApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeroApiApplication.class, args);
    }
}
