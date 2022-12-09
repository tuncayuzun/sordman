package com.tallstech.sordman.domain.price.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record TaxTypeDto(
        String country,
        @NotNull
        String type,
        @NotNull
        @Positive
        int amount) {
}
