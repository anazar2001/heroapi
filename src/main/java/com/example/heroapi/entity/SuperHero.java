// src/main/java/com/example/superhero/model/Superhero.java
package com.example.heroapi.entity;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

/**
 * SuperHero entity
 */
@Data
@Builder
@Entity
public class SuperHero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String alias;
    private String name;

    @ElementCollection
    private List<String> powers;

    @ElementCollection
    private List<String> weapons;

    private String origin;

    @ElementCollection
    private List<String> associations;
}
