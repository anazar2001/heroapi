package com.example.heroapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.heroapi.entity.SuperHero;
import com.example.heroapi.exception.SuperHeroNotFoundException;
import com.example.heroapi.exception.SuperHeroServerException;
import com.example.heroapi.repository.SuperHeroRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The default implementation of {@link SuperHeroService}.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SuperHeroServiceImpl implements SuperHeroService {

    private final SuperHeroRepository superHeroRepository;

    @Override
    public SuperHero saveSuperHero(SuperHero superHero) {
        if (superHero == null) {
            throw new IllegalArgumentException("SuperHero cannot be null");
        }

        SuperHero savedSuperHero = null;

        try {
            savedSuperHero = superHeroRepository.save(superHero);
        } catch (Exception e) {
            String error = "Failed to save SuperHero: " + superHero;

            log.error(error, e);
            throw new SuperHeroServerException(error, e);
        }

        return savedSuperHero;
    }

    @Override
    public void deleteSuperHeroById(Long id) {
        try {
            superHeroRepository.deleteById(id);
        } catch (Exception e) {
            String error = "Failed to delete SuperHero: " + id;

            log.error(error, e);
            throw new SuperHeroServerException(error, e);
        }
    }

    @Override
    public SuperHero getSuperHeroById(Long id) {
        Optional<SuperHero> superHero = null;

        try {
            superHero = superHeroRepository.findById(id);
        } catch (Exception e) {
            String error = "Failed to get SuperHero: " + id;

            log.error(error, e);
            throw new SuperHeroServerException(error, e);
        }

        if (superHero.isEmpty()) {
            throw new SuperHeroNotFoundException("SuperHero not found: " + id);
        }

        return superHero.get();
    }

    @Override
    public List<SuperHero> getAllSuperHeros() {

        List<SuperHero> superHeros = null;

        try {
            superHeros = superHeroRepository.findAll();
        } catch (Exception e) {
            String error = "Failed to get all SuperHeros";

            log.error(error, e);
            throw new SuperHeroServerException(error, e);
        }

        if (superHeros.isEmpty()) {
            throw new SuperHeroNotFoundException("SuperHeros not found");
        }

        return superHeros;
    }

    @Override
    public List<SuperHero> getSuperHerosByAssociations(List<String> associations) {

        List<SuperHero> superHeros = null;

        try {
            superHeros = superHeroRepository.findSuperHeroesByAssociations(associations);
        } catch (Exception e) {
            String error = "Failed to get SuperHeros by associations: " + associations;

            log.error(error, e);
            throw new SuperHeroServerException(error, e);
        }

        if (superHeros.isEmpty()) {
            throw new SuperHeroNotFoundException("SuperHeros by associations not found: " + associations);
        }

        return superHeros;
    }
}
