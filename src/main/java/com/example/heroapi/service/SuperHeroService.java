package com.example.heroapi.service;

import java.util.List;
import java.util.Optional;

import com.example.heroapi.entity.SuperHero;
import com.example.heroapi.exception.SuperHeroNotFoundException;
import com.example.heroapi.exception.SuperHeroServerException;

/**
 * Service for managing super heroes.
 */
public interface SuperHeroService {

    /**
     * Save a super hero.
     * 
     * @param superHero The super hero to save
     * @return The saved super hero
     * 
     * @throws IllegalArgumentException If the super hero is null
     * @throws SuperHeroServerException If the super hero cannot be saved
     */
    public SuperHero saveSuperHero(SuperHero superHero);

    /**
     * Delete a super hero by ID.
     * 
     * @param id The ID of the super hero
     * 
     * @throws SuperHeroServerException If the super hero cannot be deleted
     */
    public void deleteSuperHeroById(Long id);

    /**
     * Get a super hero by ID.
     * 
     * @param id The ID of the super hero
     * @return The super hero, if found
     * 
     * @throws SuperHeroServerException If the super hero cannot be retrieved
     * @throws SuperHeroNotFoundException If the super hero cannot be found
     */
    public SuperHero getSuperHeroById(Long id);

    /**
     * Get all super heroes.
     * 
     * @return All super heroes
     * 
     * @throws SuperHeroServerException If the super heroes cannot be retrieved
     * @throws SuperHeroNotFoundException If no super heroes are found
     */
    public List<SuperHero> getAllSuperHeros();

    /**
     * Get super heroes by associations.
     * 
     * @param associations The associations to search for
     * @return Super heroes with the given list of associations
     * 
     * @throws IllegalArgumentException If the associations are null
     * @throws SuperHeroServerException If the super heroes cannot be retrieved
     * @throws SuperHeroNotFoundException If no super heroes are found
     */
    public List<SuperHero> getSuperHerosByAssociations(List<String> associations);
}
