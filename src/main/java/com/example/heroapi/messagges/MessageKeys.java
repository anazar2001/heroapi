package com.example.heroapi.messagges;

public enum MessageKeys {
    SUPER_HERO_NOT_FOUND("SUPER_HORO_NOT_FOUND"),
    SUPER_HERO_REQUEST_VALIDATION_ERROR("SUPER_HERO_REQUEST_VALIDATION_ERROR"),
    SUPER_HERO_INTERNAL_SERVER_ERROR("SUPER_HERO_INTERNAL_SERVER_ERROR"),
    SUPER_HERO_UNEXPECTED_ERROR("SUPER_HERO_UNEXPECTED_ERROR");

    private final String key;

    MessageKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
