package com.example.heroapi;

import static java.util.Collections.emptyList;

import com.example.heroapi.dto.SuperHeroDTO;
import com.example.heroapi.entity.SuperHero;

/**
 * A helper class for creating SuperHero instances in tests.
 */
public abstract class AbstractSuperHeroTest {

    protected SuperHero createTestHero(String name) {
        return SuperHero.builder()
                .name(name)
                .alias(name)
                .origin(name)
                .powers(emptyList())
                .weapons(emptyList())
                .associations(emptyList())
                .build();
    }

    protected SuperHero createTestHero(long id, String name) {
        return SuperHero.builder()
                .id(id)
                .name(name)
                .alias(name)
                .origin(name)
                .powers(emptyList())
                .weapons(emptyList())
                .associations(emptyList())
                .build();
    }

    protected SuperHeroDTO createTestHeroDTO(String name) {
        return SuperHeroDTO.builder()
                .name(name)
                .alias(name)
                .origin(name)
                .powers(emptyList())
                .weapons(emptyList())
                .associations(emptyList())
                .build();
    }

    protected SuperHeroDTO createTestHeroDTO(long id, String name) {
        return SuperHeroDTO.builder()
                .id(id)
                .name(name)
                .alias(name)
                .origin(name)
                .powers(emptyList())
                .weapons(emptyList())
                .associations(emptyList())
                .build();
    }
}
