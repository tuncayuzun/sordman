package com.tallstech.sordman.domain.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


public record ShoppingCartDto(
        String id,
        Long partyId,
        String status,
        List<CartItemTypeDto> cartItems,
        @JsonProperty("discount")
        BigDecimal cartDiscount,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        BigDecimal totalPrice,
        LocalDateTime validityPeriod) {}
