package com.example.heroapi.converter;
import java.util.Objects;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.heroapi.dto.SuperHeroDTO;
import com.example.heroapi.entity.SuperHero;

/**
 * Converter from {@link SuperHero} to {@link SuperHeroDTO}.
 * */
@Component
public class SuperHeroToSuperHeroDTOConverter implements Converter<SuperHero, SuperHeroDTO> {

    @Override
    public SuperHeroDTO convert(SuperHero source) {

        if (Objects.isNull(source)) {
            throw new IllegalArgumentException("Source object cannot be null");
        }

        return SuperHeroDTO.builder()
            .id(source.getId())
            .alias(source.getAlias())
            .name(source.getName())
            .powers(source.getPowers())
            .weapons(source.getWeapons())
            .origin(source.getOrigin())
            .associations(source.getAssociations())
            .build();
    }
}