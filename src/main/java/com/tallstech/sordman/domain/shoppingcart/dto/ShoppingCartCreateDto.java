package com.tallstech.sordman.domain.shoppingcart.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record ShoppingCartCreateDto(
        @NotNull(message = "Must contain party id")
        @Positive(message = "Must be positive")
        Long partyId,
        String status,
        List<CartItemTypeDto> cartItems,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        BigDecimal totalPrice,
        LocalDateTime validityPeriod) {}
