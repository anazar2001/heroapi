package com.example.heroapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.heroapi.entity.SuperHero;
import com.example.heroapi.exception.SuperHeroNotFoundException;
import com.example.heroapi.exception.SuperHeroServerException;
import com.example.heroapi.repository.SuperHeroRepository;

import lombok.RequiredArgsConstructor;

/**
 * The default implementation of {@link SuperHeroService}.
 */
@RequiredArgsConstructor
@Service
public class SuperHeroServiceImpl implements SuperHeroService {

    private final SuperHeroRepository superHeroRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public SuperHero saveSuperHero(SuperHero superHero) {
        if (superHero == null) {
            throw new IllegalArgumentException("SuperHero cannot be null");
        }

        SuperHero savedSuperHero = null;

        try {
            savedSuperHero = superHeroRepository.save(superHero);
        } catch (Exception e) {
            throw new SuperHeroServerException("Failed to save SuperHero: " + superHero, e);
        }

        return savedSuperHero;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteSuperHeroById(Long id) {
        try {
            superHeroRepository.deleteById(id);
        } catch (Exception e) {
            throw new SuperHeroServerException("Failed to delete SuperHero: " + id, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SuperHero getSuperHeroById(Long id) {
        Optional<SuperHero> superHero = null;

        try {
            superHero = superHeroRepository.findById(id);
        } catch (Exception e) {
            throw new SuperHeroServerException("Failed to get SuperHero: " + id, e);
        }

        if (superHero.isEmpty()) {
            throw new SuperHeroNotFoundException("SuperHero not found: " + id);
        }

        return superHero.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SuperHero> getAllSuperHeroes() {

        List<SuperHero> superHeroes = null;

        try {
            superHeroes = superHeroRepository.findAll();
        } catch (Exception e) {
            throw new SuperHeroServerException("Failed to get all SuperHeroes", e);
        }

        if (superHeroes.isEmpty()) {
            throw new SuperHeroNotFoundException("SuperHeroes not found");
        }

        return superHeroes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SuperHero> getSuperHeroesByAssociations(List<String> associations) {

        List<SuperHero> superHeroes = null;

        try {
            superHeroes = superHeroRepository.findSuperHeroesByAssociations(associations);
        } catch (Exception e) {
            throw new SuperHeroServerException("Failed to get SuperHeros by associations: " + associations, e);
        }

        if (superHeroes.isEmpty()) {
            throw new SuperHeroNotFoundException("SuperHeros by associations not found: " + associations);
        }

        return superHeroes;
    }
}
