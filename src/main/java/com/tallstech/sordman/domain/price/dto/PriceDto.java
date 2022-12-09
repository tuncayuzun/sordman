package com.tallstech.sordman.domain.price.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record PriceDto(
        @NotNull
        String id,
        String code,
        String name,
        String type,
        String status,
        BigDecimal basePrice,
        BigDecimal salesPrice,
        String currency,
        TaxTypeDto tax) {
}
