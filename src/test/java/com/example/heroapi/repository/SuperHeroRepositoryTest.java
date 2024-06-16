// src/test/java/com/example/superhero/repository/SuperHeroRepositoryTest.java
package com.example.heroapi.repository;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import com.example.heroapi.configuration.DockerComposeTestConfig;
import com.example.heroapi.entity.SuperHero;

@DataJpaTest(excludeAutoConfiguration = FlywayAutoConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = {DockerComposeTestConfig.Initializer.class})
class SuperHeroRepositoryTest {

    @Autowired
    private SuperHeroRepository superHeroRepository;

    @Test
    void getCreateUpdateGetUpdateRemove() {
        SuperHero hero = createTestHero();
        SuperHero savedHero = superHeroRepository.save(hero);

        assertNotNull(savedHero, "Saved hero should not be null");
        assertEquals(hero.getAlias(), savedHero.getAlias(), "Alias should match");
        assertEquals(hero.getName(), savedHero.getName(), "Name should match");
        assertEquals(hero.getOrigin(), savedHero.getOrigin(), "Origin should match");
        assertEquals(hero.getPowers(), savedHero.getPowers(), "Powers should match");
        assertEquals(hero.getWeapons(), savedHero.getWeapons(), "Weapons should match");
        assertEquals(hero.getAssociations(), savedHero.getAssociations(), "Associations should match");

        SuperHero loadedHero = superHeroRepository.findById(savedHero.getId()).orElse(null);
        assertNotNull(loadedHero, "Loaded hero should not be null");
        assertEquals(savedHero, loadedHero, "Loaded hero should match saved hero");

        loadedHero.setAlias("alias2");
        loadedHero.setName("name2");
        loadedHero.setOrigin("origin2");
        loadedHero.setPowers(new ArrayList<>(List.of("power11", "power22")));
        loadedHero.setWeapons(null);
        loadedHero.setAssociations(new ArrayList<>(List.of("association11", "association22", "association23")));
        savedHero = superHeroRepository.save(loadedHero);
        assertEquals(savedHero, loadedHero, "Saved hero should match loaded hero");
        
        superHeroRepository.delete(savedHero);
        assertNull(superHeroRepository.findById(savedHero.getId()).orElse(null), "Hero should be removed");
    }

    private SuperHero createTestHero() {
        return SuperHero.builder()
                .alias("alias1")
                .name("name1")
                .origin("origin1")
                .powers(Arrays.asList("power11", "power12"))
                .weapons(Arrays.asList("weapon11", "weapon12"))
                .associations(Arrays.asList("association11", "association12", "association13"))
                .build();
    }

    @Test
    void testFindSuperHeroesByTwoAssociations() {
        List<String> associations = Arrays.asList("avengers", "thanos");
        List<SuperHero> heros = superHeroRepository.findSuperHeroesByAssociations(associations);
        assertNotNull(heros, "Should not return null");
        assertEquals(1, heros.size(), "Should return 1 hero");
    }

    @Test
    void testFindSuperHeroesByZeroAssociations() {
        List<SuperHero> heros = superHeroRepository.findSuperHeroesByAssociations(emptyList());
        assertNotNull(heros, "Should not return null");
        assertEquals(0, heros.size(), "Should return 0 hero");
    }
}
