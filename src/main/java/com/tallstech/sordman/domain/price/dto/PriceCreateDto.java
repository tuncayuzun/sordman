package com.tallstech.sordman.domain.price.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


@Valid
public record PriceCreateDto(
        @JsonProperty(required = true)
        @NotNull
        String name,
        @JsonProperty(required = true)
        @NotBlank
        @NotNull
        String type,
        String status,
        @NotNull
        @Positive
        BigDecimal basePrice,
        @NotNull
        String currency,
        @NotNull
        TaxTypeDto tax) {
}
