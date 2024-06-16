package com.example.heroapi.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.heroapi.dto.SuperHeroDTO;
import com.example.heroapi.entity.SuperHero;
import com.example.heroapi.service.SuperHeroService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/superheroes")
@RequiredArgsConstructor
@Validated
public class SuperHeroController {

    private final SuperHeroService superHeroService;
    private final ConversionService conversionService;

    /**
     * Save a super hero DTO.
     * 
     * URI - POST: /superheroes
     * 
     * @param superHeroDTO The super hero DTO to save {@link SuperHeroDTO}
     * @return Saved super hero DTO {@link SuperHeroDTO}
     * 
     *         Returned http codes:
     *         - 201: Super hero saved successfully
     *         - 400: Bad request
     *         - 500: Internal server error
     */
    @PostMapping
    public ResponseEntity<SuperHeroDTO> saveSuperHero(@Valid @RequestBody SuperHeroDTO superHeroDTO) {

        SuperHero superHero = conversionService.convert(superHeroDTO, SuperHero.class);
        SuperHero savedSuperHero = superHeroService.saveSuperHero(superHero);
        SuperHeroDTO savedSuperHeroDTO = conversionService.convert(savedSuperHero, SuperHeroDTO.class);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedSuperHero.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedSuperHeroDTO);
    }

    /**
     * Delete a super hero by ID.
     * 
     * URI - DELETE: /superheroes/{id}
     * 
     * @param id Super hero ID.
     * @return No content
     * 
     *         Returned http codes:
     *         - 204: Super hero deleted successfully
     *         - 500: Internal server error
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSuperHero(@PathVariable Long id) {

        superHeroService.deleteSuperHeroById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get a super hero by ID.
     * 
     * URI - GET: /superheroes/{id}
     * 
     * @param id Super hero ID.
     * @return Super hero DTO {@link SuperHeroDTO}
     * 
     *         Returned http codes:
     *         - 200: Super hero found
     *         - 404: Super hero not found
     *         - 500: Internal server error
     */
    @GetMapping("/{id}")
    public ResponseEntity<SuperHeroDTO> getSuperHeroById(@PathVariable Long id) {
        SuperHero superHero = superHeroService.getSuperHeroById(id);
        return ResponseEntity.ok(conversionService.convert(superHero, SuperHeroDTO.class));
    }

    /**
     * Get all super heroes.
     * 
     * URI - GET: /superheroes
     * 
     * @return List of all super hero DTOs {@link SuperHeroDTO}
     * 
     *         Returned http codes:
     *         - 200: Super heroes found
     *         - 404: Super heroes not found
     *         - 500: Internal server error
     */
    @GetMapping
    public ResponseEntity<List<SuperHeroDTO>> getAllSuperheroes() {

        List<SuperHero> superHeroes = superHeroService.getAllSuperHeroes();
        List<SuperHeroDTO> superHeroDTOs = superHeroes.stream()
                .map(hero -> conversionService.convert(hero, SuperHeroDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(superHeroDTOs);
    }

    /**
     * Get super heroes by associations.
     * 
     * URI - GET: /superheroes/search?association=avengers&association=thanos
     * 
     * @param associations List of associations to search for.
     * @return List of super hero DTOs {@link SuperHeroDTO} which have all of the
     *         given associations.
     * 
     *         Returned http codes:
     *         - 200: Super heroes found
     *         - 404: Super heroes not found
     *         - 500: Internal server error
     */
    @GetMapping("/search")
    public ResponseEntity<List<SuperHeroDTO>> getSuperHeroesByAssociations(
            @RequestParam(name = "association") List<String> associations) {

        List<SuperHero> superHeroes = superHeroService.getSuperHeroesByAssociations(associations);
        List<SuperHeroDTO> superHeroDTOs = superHeroes.stream()
                .map(hero -> conversionService.convert(hero, SuperHeroDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(superHeroDTOs);
    }
}