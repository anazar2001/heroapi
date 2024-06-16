package com.example.heroapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.heroapi.AbstractSuperHeroTest;
import com.example.heroapi.dto.SuperHeroDTO;
import com.example.heroapi.entity.SuperHero;
import com.example.heroapi.exception.SuperHeroNotFoundException;
import com.example.heroapi.exception.SuperHeroServerException;
import com.example.heroapi.service.SuperHeroService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(SuperHeroController.class)
class SuperHeroControllerTest extends AbstractSuperHeroTest {

    @MockBean
    private SuperHeroService superHeroService;

    @MockBean
    private ConversionService conversionService;

    @MockBean(name = "mvcConversionService")
    private FormattingConversionService formattingConversionService;

    @InjectMocks
    private SuperHeroController superHeroController;
    
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        reset(superHeroService);
    }

    @Test
    void testSaveSuperHero() throws Exception {
        SuperHeroDTO superHeroDTO = createTestHeroDTO("Superman");
        SuperHeroDTO savedSuperHeroDTO = createTestHeroDTO(1L, "Superman");
        SuperHero superHero = createTestHero("Superman");
        SuperHero savedSuperHero = createTestHero(1L, "Superman");

        when(formattingConversionService.convert(superHeroDTO, SuperHero.class)).thenReturn(superHero);
        when(superHeroService.saveSuperHero(any(SuperHero.class))).thenReturn(savedSuperHero);
        when(formattingConversionService.convert(savedSuperHero, SuperHeroDTO.class)).thenReturn(savedSuperHeroDTO);

        mockMvc.perform(post("/superheroes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(superHeroDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(savedSuperHeroDTO)));

        verify(superHeroService).saveSuperHero(superHero);
    }

    @Test
    void testSaveSuperHeroBadRequest() throws Exception {
        SuperHeroDTO superHeroDTO = createTestHeroDTO("Superman");
        superHeroDTO.setOrigin(null); // Not allowed to be null

        SuperHero superHero = createTestHero("Superman");

        when(formattingConversionService.convert(superHeroDTO, SuperHero.class)).thenReturn(superHero);

        mockMvc.perform(post("/superheroes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(superHeroDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSaveSuperHeroFailedServerError() throws Exception {
        SuperHeroDTO superHeroDTO = createTestHeroDTO("Superman");
        SuperHeroDTO savedSuperHeroDTO = createTestHeroDTO(1L, "Superman");
        SuperHero superHero = createTestHero("Superman");
        SuperHero savedSuperHero = createTestHero(1L, "Superman");

        when(formattingConversionService.convert(superHeroDTO, SuperHero.class)).thenReturn(superHero);
        doThrow(new SuperHeroServerException("Failed to save SuperHero")).when(superHeroService).saveSuperHero(superHero);
        when(formattingConversionService.convert(savedSuperHero, SuperHeroDTO.class)).thenReturn(savedSuperHeroDTO);

        mockMvc.perform(post("/superheroes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(superHeroDTO)))
                .andExpect(status().isInternalServerError());

        verify(superHeroService).saveSuperHero(superHero);
    }

    @Test
    void testDeleteSuperHero() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/superheroes/{id}", id))
                .andExpect(status().isNoContent());

        verify(superHeroService).deleteSuperHeroById(id);
    }

    @Test
    void testDeleteSuperHeroInternalError() throws Exception {

        Long superHeroId = 1L;
        doThrow(new SuperHeroServerException("Failed to delete SuperHero")).when(superHeroService).deleteSuperHeroById(superHeroId);

        mockMvc.perform(delete("/superheroes/{id}", superHeroId))
                .andExpect(status().isInternalServerError());

        verify(superHeroService).deleteSuperHeroById(superHeroId);
    }

    @Test
    void testGetSuperHeroById() throws Exception {

        Long superHeroId = 1L;
        SuperHero superHero = createTestHero(1L, "Superman");
        SuperHeroDTO superHeroDTO = createTestHeroDTO(1L, "Superman");

        when(superHeroService.getSuperHeroById(superHeroId)).thenReturn(superHero);
        when(conversionService.convert(superHero, SuperHeroDTO.class)).thenReturn(superHeroDTO);

        mockMvc.perform(get("/superheroes/{id}", superHeroId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(superHeroDTO)));

        verify(superHeroService).getSuperHeroById(superHeroId);
    }

    @Test
    void testGetSuperHeroByIdInternalError() throws Exception {

        Long superHeroId = 1L;

        doThrow(new SuperHeroServerException("Failed to get SuperHero")).when(superHeroService).getSuperHeroById(superHeroId);

        mockMvc.perform(get("/superheroes/{id}", superHeroId))
                .andExpect(status().isInternalServerError());

        verify(superHeroService).getSuperHeroById(superHeroId);
    }

    @Test
    void testGetSuperHeroByIdNotFoundError() throws Exception {

        Long superHeroId = 1L;

        doThrow(new SuperHeroNotFoundException("SuperHero not found")).when(superHeroService).getSuperHeroById(superHeroId);

        mockMvc.perform(get("/superheroes/{id}", superHeroId))
                .andExpect(status().isNotFound());

        verify(superHeroService).getSuperHeroById(superHeroId);
    }

    @Test
    void testGetAllSuperHeroes() throws Exception {
        List<SuperHero> superHeroes = List.of(createTestHero(1L, "Superman1"), createTestHero(2L, "Superman2"));
        List<SuperHeroDTO> superHeroesDTOs = List.of(createTestHeroDTO(1L, "Superman1"), createTestHeroDTO(2L, "Superman2"));

        when(superHeroService.getAllSuperHeros()).thenReturn(superHeroes);
        when(conversionService.convert(any(SuperHero.class), eq(SuperHeroDTO.class)))
                .thenReturn(superHeroesDTOs.get(0), superHeroesDTOs.get(1));

        mockMvc.perform(get("/superheroes"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(superHeroesDTOs)));

        verify(superHeroService).getAllSuperHeros();
    }

    @Test
    void testGetAllSuperHeroesInternalError() throws Exception {

        List<SuperHeroDTO> superHeroesDTOs = List.of(createTestHeroDTO(1L, "Superman1"), createTestHeroDTO(2L, "Superman2"));

        doThrow(new SuperHeroServerException("Failed to get all SuperHeros")).when(superHeroService).getAllSuperHeros();
        when(conversionService.convert(any(SuperHero.class), eq(SuperHeroDTO.class)))
                .thenReturn(superHeroesDTOs.get(0), superHeroesDTOs.get(1));

        mockMvc.perform(get("/superheroes"))
                .andExpect(status().isInternalServerError());

        verify(superHeroService).getAllSuperHeros();
    }

    @Test
    void testGetAllSuperHeroesNotFoundError() throws Exception {

        List<SuperHeroDTO> superHeroesDTOs = List.of(createTestHeroDTO(1L, "Superman1"), createTestHeroDTO(2L, "Superman2"));

        doThrow(new SuperHeroNotFoundException("Failed to get all SuperHeros")).when(superHeroService).getAllSuperHeros();
        when(conversionService.convert(any(SuperHero.class), eq(SuperHeroDTO.class)))
                .thenReturn(superHeroesDTOs.get(0), superHeroesDTOs.get(1));

        mockMvc.perform(get("/superheroes"))
                .andExpect(status().isNotFound());

        verify(superHeroService).getAllSuperHeros();
    }

    @Test
    void testGetSuperHeroesByAssociations() throws Exception {

        List<String> associations = List.of("Justice League", "Avengers");
        List<SuperHero> superHeroes = List.of(createTestHero(1L, "Superman1"), createTestHero(2L, "Superman2"));
        List<SuperHeroDTO> superHeroesDTOs = List.of(createTestHeroDTO(1L, "Superman1"), createTestHeroDTO(2L, "Superman2"));

        when(superHeroService.getSuperHerosByAssociations(associations)).thenReturn(superHeroes);
        when(conversionService.convert(any(SuperHero.class), eq(SuperHeroDTO.class)))
                .thenReturn(superHeroesDTOs.get(0), superHeroesDTOs.get(1));

        mockMvc.perform(get("/superheroes/search")
                .param("associations", "Justice League", "Avengers"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(superHeroesDTOs)));

        verify(superHeroService).getSuperHerosByAssociations(associations);
    }

    @Test
    void testGetSuperHeroesByAssociationsInternalError() throws Exception {

        List<String> associations = List.of("Justice League", "Avengers");
        List<SuperHeroDTO> superHeroesDTOs = List.of(createTestHeroDTO(1L, "Superman1"), createTestHeroDTO(2L, "Superman2"));

        doThrow(new SuperHeroServerException("Failed to get all SuperHeros")).when(superHeroService).getSuperHerosByAssociations(associations);
        when(conversionService.convert(any(SuperHero.class), eq(SuperHeroDTO.class)))
                .thenReturn(superHeroesDTOs.get(0), superHeroesDTOs.get(1));

        mockMvc.perform(get("/superheroes/search")
                .param("associations", "Justice League", "Avengers"))
                .andExpect(status().isInternalServerError());

        verify(superHeroService).getSuperHerosByAssociations(associations);
    }

    @Test
    void testGetSuperHeroesByAssociationsNotFoundError() throws Exception {

        List<String> associations = List.of("Justice League", "Avengers");
        List<SuperHeroDTO> superHeroesDTOs = List.of(createTestHeroDTO(1L, "Superman1"), createTestHeroDTO(2L, "Superman2"));

        doThrow(new SuperHeroNotFoundException("Failed to get all SuperHeros")).when(superHeroService).getSuperHerosByAssociations(associations);
        when(conversionService.convert(any(SuperHero.class), eq(SuperHeroDTO.class)))
                .thenReturn(superHeroesDTOs.get(0), superHeroesDTOs.get(1));

        mockMvc.perform(get("/superheroes/search")
                .param("associations", "Justice League", "Avengers"))
                .andExpect(status().isNotFound());

        verify(superHeroService).getSuperHerosByAssociations(associations);
    }
}