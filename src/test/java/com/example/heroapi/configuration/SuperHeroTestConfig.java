package com.example.heroapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;

@Configuration
public class SuperHeroTestConfig {
    @Bean
    public FormattingConversionService formattingConversionService() {
        return new DefaultFormattingConversionService();
    }
}