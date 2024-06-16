package com.example.heroapi.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;

/**
 * Test configuration for SuperHero API Integration tests.
 */
@TestConfiguration
public class SuperHeroTestConfig {

    @Bean
    public TestRestTemplate restTemplate() {
        return new TestRestTemplate();
    }
}