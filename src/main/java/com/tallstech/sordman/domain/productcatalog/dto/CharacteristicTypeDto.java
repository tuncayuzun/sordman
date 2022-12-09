package com.tallstech.sordman.domain.productcatalog.dto;

public record CharacteristicTypeDto(
        String name,
        Object value,
        String type,
        String description) {
}
