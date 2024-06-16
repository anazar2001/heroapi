package com.example.heroapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.GenericConversionService;

import com.example.heroapi.converter.SuperHeroDTOToSuperHeroConverter;
import com.example.heroapi.converter.SuperHeroToSuperHeroDTOConverter;

@Configuration
public class SuperHeroConfig {

    @Bean
    public ConversionService conversionService() {
        GenericConversionService conversionService = new GenericConversionService();
        conversionService.addConverter(new SuperHeroDTOToSuperHeroConverter());
        conversionService.addConverter(new SuperHeroToSuperHeroDTOConverter());
        return conversionService;
    }
}
