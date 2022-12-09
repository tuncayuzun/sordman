package com.tallstech.sordman.domain.productcatalog.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tallstech.sordman.annotation.RequiredPrice;
import jakarta.validation.constraints.NotNull;

public record ProductCatalogCreateDto(
        @NotNull
        String code,
        @NotNull
        String name,
        @NotNull
        String type,
        @NotNull
        String category,
        @NotNull
        String subCategory,
        @NotNull
        String status,
        @JsonProperty(defaultValue = "try")
        String defaultCurrency,
        @NotNull(message = "Must contain at least price id")
        @RequiredPrice
        List<IdTypeDto> relations,
        @NotNull
        DescriptionTypeDto description,
        SalesTypeDto salesType,
        List<OfferingInfoTypeDto> offerings) {
}
