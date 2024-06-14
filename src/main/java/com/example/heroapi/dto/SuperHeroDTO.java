package com.example.heroapi.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * SuperHero DTO object.
 */
@Data
@Builder
public class SuperHeroDTO {

    private Long id;
    private String alias;
    private String name;
    private List<String> powers;
    private List<String> weapons;
    private String origin;
    private List<String> associations;
}
