package com.example.heroapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.heroapi.AbstractSuperHeroTest;
import com.example.heroapi.entity.SuperHero;
import com.example.heroapi.exception.SuperHeroNotFoundException;
import com.example.heroapi.exception.SuperHeroServerException;
import com.example.heroapi.repository.SuperHeroRepository;

@ExtendWith(MockitoExtension.class)
class SuperHeroServiceImplTest extends AbstractSuperHeroTest {

    @Mock
    private SuperHeroRepository superHeroRepository;

    @InjectMocks
    private SuperHeroServiceImpl superHeroService;

    @BeforeEach
    void setUp() {
        reset(superHeroRepository);
    }

    @Test
    void testSaveSuperHero() {

        // given
        SuperHero superHero = createTestHero("name1");
        ArgumentCaptor<SuperHero> superHeroCaptor = ArgumentCaptor.forClass(SuperHero.class);

        // when
        superHeroService.saveSuperHero(superHero);

        // then
        verify(superHeroRepository).save(superHeroCaptor.capture());

        SuperHero savedHero = superHeroCaptor.getValue();
        assertNotNull(savedHero, "Saved hero should not be null");
        assertEquals(superHero.getName(), savedHero.getName(), "Name should match");
    }

    @Test
    void testSaveSuperHeroFails() {

        // given
        SuperHero superHero = createTestHero("name1");

        when(superHeroRepository.save(superHero)).thenThrow(new RuntimeException("Failed to save SuperHero"));

        // when
        SuperHeroServerException exception = assertThrows(SuperHeroServerException.class,
                () -> superHeroService.saveSuperHero(superHero));

        // then
        assertNotNull(exception, "Exception should not be null");
        assertTrue(exception.getMessage().contains("Failed to save SuperHero"), "Exception message should match");
    }

    @Test
    void testDeleteSuperHero() {

        // given
        SuperHero superHero = createTestHero(1L, "name1");

        ArgumentCaptor<Long> superHeroCaptor = ArgumentCaptor.forClass(Long.class);

        // when
        superHeroService.deleteSuperHeroById(superHero.getId());

        // then
        verify(superHeroRepository).deleteById(superHeroCaptor.capture());
        assertEquals(superHero.getId(), superHeroCaptor.getValue(), "Id should match");
    }

    @Test
    void testDeleteSuperHeroFails() {

        // given
        long superHeroId = 1L;

        doThrow(new RuntimeException("Failed to delete SuperHero")).when(superHeroRepository).deleteById(superHeroId);

        // when
        SuperHeroServerException exception = assertThrows(SuperHeroServerException.class,
                () -> superHeroService.deleteSuperHeroById(superHeroId));

        // then
        assertNotNull(exception, "Exception should not be null");
        assertEquals("Failed to delete SuperHero: 1", exception.getMessage(), "Exception message should match");
    }

    @Test
    void testGetAllSuperHeros() {

        // given
        SuperHero superHero1 = createTestHero("name1");
        SuperHero superHero2 = createTestHero("name2");

        when(superHeroRepository.findAll()).thenReturn(List.of(superHero1, superHero2));

        // when
        List<SuperHero> superHeros = superHeroService.getAllSuperHeros();

        // then
        verify(superHeroRepository).findAll();
        assertNotNull(superHeros, "SuperHeros should not be null");
        assertEquals(2, superHeros.size(), "SuperHeros should contain 2 elements");
        assertEquals("name1", superHeros.get(0).getName(), "First hero name should match");
        assertEquals("name2", superHeros.get(1).getName(), "Second hero name should match");
    }

    @Test
    void testGetAllSuperHerosNotFound() {

        // given
        when(superHeroRepository.findAll()).thenReturn(List.of());

        // when
        SuperHeroNotFoundException exception = assertThrows(SuperHeroNotFoundException.class,
                () -> superHeroService.getAllSuperHeros());

        // then
        assertNotNull(exception, "Exception should not be null");
        assertEquals("SuperHeros not found", exception.getMessage(), "Exception message should match");
    }

    @Test
    void testGetAllSuperHerosFails() {

        // given
        when(superHeroRepository.findAll()).thenThrow(new RuntimeException("Failed to get all SuperHeros"));

        // when
        SuperHeroServerException exception = assertThrows(SuperHeroServerException.class,
                () -> superHeroService.getAllSuperHeros());

        // then
        assertNotNull(exception, "Exception should not be null");
        assertEquals("Failed to get all SuperHeros", exception.getMessage(), "Exception message should match");
    }

    @Test
    void testGetSuperHeroById() {

        // given
        SuperHero superHero = createTestHero(1L, "name1");
        long superHeroId = superHero.getId();

        when(superHeroRepository.findById(superHero.getId())).thenReturn(java.util.Optional.of(superHero));

        // when
        SuperHero loadedHero = superHeroService.getSuperHeroById(superHeroId);

        // then
        verify(superHeroRepository).findById(superHero.getId());
        assertNotNull(loadedHero, "Loaded hero should not be null");
        assertEquals(superHero, loadedHero, "Loaded hero should match expected hero");
    }

    @Test
    void testGetSuperHeroByIdNotFound() {

        // given
        long superHeroId = 1L;
        when(superHeroRepository.findById(superHeroId)).thenReturn(Optional.empty());

        // when
        SuperHeroNotFoundException exception = assertThrows(SuperHeroNotFoundException.class,
                () -> superHeroService.getSuperHeroById(superHeroId));

        // then
        assertNotNull(exception, "Exception should not be null");
        assertEquals("SuperHero not found: 1", exception.getMessage(), "Exception message should match");
    }

    @Test
    void testGetSuperHeroByIdFails() {

        // given
        long superHeroId = 1L;
        when(superHeroRepository.findById(superHeroId)).thenThrow(new RuntimeException("Failed to get SuperHero"));

        // when
        SuperHeroServerException exception = assertThrows(SuperHeroServerException.class,
                () -> superHeroService.getSuperHeroById(superHeroId));

        // then
        assertNotNull(exception, "Exception should not be null");
        assertEquals("Failed to get SuperHero: 1", exception.getMessage(), "Exception message should match");
    }

    @Test
    void testGetSuperHerosByAssociations() {

        // given
        List<String> associations = List.of("association1", "association2");
        SuperHero superHero1 = createTestHero("name1");
        SuperHero superHero2 = createTestHero("name2");

        when(superHeroRepository.findSuperHeroesByAssociations(associations))
                .thenReturn(List.of(superHero1, superHero2));

        // when
        List<SuperHero> superHeros = superHeroService.getSuperHerosByAssociations(associations);

        // then
        verify(superHeroRepository).findSuperHeroesByAssociations(associations);
        assertNotNull(superHeros, "SuperHeros should not be null");
        assertEquals(2, superHeros.size(), "SuperHeros should contain 2 elements");
        assertEquals("name1", superHeros.get(0).getName(), "First hero name should match");
        assertEquals("name2", superHeros.get(1).getName(), "Second hero name should match");
    }

    @Test
    void testGetSuperHerosByAssociationsNotFound() {

        // given
        List<String> associations = List.of("association1", "association2");
        when(superHeroRepository.findSuperHeroesByAssociations(associations)).thenReturn(List.of());

        // when
        SuperHeroNotFoundException exception = assertThrows(SuperHeroNotFoundException.class,
                () -> superHeroService.getSuperHerosByAssociations(associations));

        // then
        assertNotNull(exception, "Exception should not be null");
        assertEquals("SuperHeros by associations not found: [association1, association2]", exception.getMessage(),
                "Exception message should match");
    }

    @Test
    void testGetSuperHerosByAssociationsFails() {

        // given
        List<String> associations = List.of("association1", "association2");
        when(superHeroRepository.findSuperHeroesByAssociations(associations))
                .thenThrow(new RuntimeException("Failed to get SuperHeros by associations"));

        // when
        SuperHeroServerException exception = assertThrows(SuperHeroServerException.class,
                () -> superHeroService.getSuperHerosByAssociations(associations));

        // then
        assertNotNull(exception, "Exception should not be null");
        assertEquals("Failed to get SuperHeros by associations: [association1, association2]", exception.getMessage(),
                "Exception message should match");
    }
}