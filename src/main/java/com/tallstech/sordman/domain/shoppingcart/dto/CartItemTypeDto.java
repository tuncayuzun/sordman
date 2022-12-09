package com.tallstech.sordman.domain.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;


public record CartItemTypeDto(
        String catalogId,
        String code,
        String name,
        String type,
        String category,
        String subCategory,
        PriceInfoDto price,
        int quantity,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        BigDecimal totalPrice) {}
