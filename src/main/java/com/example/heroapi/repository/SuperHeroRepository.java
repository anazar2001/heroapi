package com.example.heroapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.heroapi.entity.SuperHero;

public interface SuperHeroRepository extends JpaRepository<SuperHero, Long> {

    @Query("SELECT sh FROM SuperHero sh " +
           "WHERE EXISTS (" +
           "    SELECT 1 FROM SuperHero sha " +
           "    JOIN sha.associations a " +
           "    WHERE a IN :associations AND sha.id = sh.id " +
           "    GROUP BY sha.id " +
           "    HAVING COUNT(a) = :#{#associations.size()}" +
           ")")
    List<SuperHero> findSuperHeroesByAssociations(@Param("associations") List<String> associations);
}
