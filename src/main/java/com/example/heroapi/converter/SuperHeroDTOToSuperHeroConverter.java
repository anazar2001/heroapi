package com.example.heroapi.converter;
import java.util.Objects;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.heroapi.dto.SuperHeroDTO;
import com.example.heroapi.entity.SuperHero;

/**
 * Converter from {@link SuperHeroDTO} to {@link SuperHero}.
 * */
@Component
public class SuperHeroDTOToSuperHeroConverter implements Converter<SuperHeroDTO, SuperHero> {

    @Override
    public SuperHero convert(SuperHeroDTO source) {

        if (Objects.isNull(source)) {
            throw new IllegalArgumentException("Source object cannot be null");
        }

        return SuperHero.builder()
            .alias(source.getAlias())
            .name(source.getName())
            .powers(source.getPowers())
            .weapons(source.getWeapons())
            .origin(source.getOrigin())
            .associations(source.getAssociations())
            .build();
    }
}