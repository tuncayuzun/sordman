package com.tallstech.sordman.domain.salesorder.dto;

public record CharacteristicTypeDto(
        String name,
        Object value,
        String type,
        String description) {}
