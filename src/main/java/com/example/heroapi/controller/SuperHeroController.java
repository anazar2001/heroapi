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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSuperHero(@PathVariable Long id) {

        superHeroService.deleteSuperHeroById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuperHeroDTO> getSuperHeroById(@PathVariable Long id) {
        SuperHero superHero = superHeroService.getSuperHeroById(id);
        return ResponseEntity.ok(conversionService.convert(superHero, SuperHeroDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<SuperHeroDTO>> getAllSuperheroes() {

        List<SuperHero> superHeroes = superHeroService.getAllSuperHeros();
        List<SuperHeroDTO> superHeroDTOs = superHeroes.stream()
                .map(hero -> conversionService.convert(hero, SuperHeroDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(superHeroDTOs);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SuperHeroDTO>> getSuperHeroesByAssociations(@RequestParam List<String> associations) {

        List<SuperHero> superHeroes = superHeroService.getSuperHerosByAssociations(associations);
        List<SuperHeroDTO> superHeroDTOs = superHeroes.stream()
                .map(hero -> conversionService.convert(hero, SuperHeroDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(superHeroDTOs);
    }
}