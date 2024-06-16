package com.example.heroapi.entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Entity which represents a super hero.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "super_hero", schema = "hero_user")
public class SuperHero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String alias;
    private String name;
    private String origin;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "super_hero_associations", joinColumns = @JoinColumn(name = "super_hero_id"))
    @Column(name = "association")
    private List<String> associations;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "super_hero_powers", joinColumns = @JoinColumn(name = "super_hero_id"))
    @Column(name = "power")
    private List<String> powers;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "super_hero_weapons", joinColumns = @JoinColumn(name = "super_hero_id"))
    @Column(name = "weapon")
    private List<String> weapons;
}