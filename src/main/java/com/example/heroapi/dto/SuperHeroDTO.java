package com.example.heroapi.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * SuperHero DTO object.
 */
@Data
@Builder
public class SuperHeroDTO {

    private Long id;

    @NotNull(message = "Alias must not be null")
    private String alias;

    @NotNull(message = "Name must not be null")
    private String name;

    @NotNull(message = "Powers must not be null")
    private List<String> powers;

    private List<String> weapons;

    @NotNull(message = "Origin must not be null")
    private String origin;

    @NotNull(message = "Associations must not be null")
    private List<String> associations;
}
