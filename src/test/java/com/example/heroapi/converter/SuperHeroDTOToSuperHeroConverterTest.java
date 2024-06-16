package com.example.heroapi.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.converter.Converter;

import com.example.heroapi.dto.SuperHeroDTO;
import com.example.heroapi.entity.SuperHero;

class SuperHeroDTOToSuperHeroConverterTest {

    private static Converter<SuperHeroDTO, SuperHero> converter;

    @BeforeAll
    public static void setUp() {
        converter = new SuperHeroDTOToSuperHeroConverter();
    }

    @Test
    void testConvertNoSourceProvided() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> converter.convert(null));
        assertEquals("Source object cannot be null", exception.getMessage(), "Wrong exception message");
    }

    @Test
    void testConvert() {

        // given
        SuperHeroDTO superHeroDto = SuperHeroDTO.builder()
                .id(1L)
                .alias("Iron Man")
                .name("Tony Stark")
                .powers(List.of("genius-intelligence", "wealth"))
                .origin("Kidnapped in Afghanistan, created the first iron-man suit to escape.")
                .associations(List.of("war-machine", "avengers", "jarvis", "thanos", "pepper-potts"))
                .build();

        // when
        SuperHero superHero = converter.convert(superHeroDto);

        // then
        assertNotNull(superHeroDto, "SuperHeroDto should not be null");
        assertEquals(superHero.getId(), superHeroDto.getId(), "Id should match");
        assertEquals(superHero.getAlias(), superHeroDto.getAlias(), "Alias should match");
        assertEquals(superHero.getName(), superHeroDto.getName(), "Name should match");
        assertEquals(superHero.getPowers(), superHeroDto.getPowers(), "Powers should match");
        assertEquals(superHero.getOrigin(), superHeroDto.getOrigin(), "Origin should match");
        assertEquals(superHero.getAssociations(), superHeroDto.getAssociations(), "Associations should match");
    }
}
