package com.tallstech.sordman.domain.payment.dto;

public record CharacteristicTypeDto(
        String name,
        Object value,
        String type,
        String description) {
}
