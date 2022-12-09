package com.tallstech.sordman.domain.salesorder.dto;

import java.math.BigDecimal;
import java.util.List;


public record OrderItemTypeDto(
        BigDecimal price,
        List<CartItemTypeDto> items) {}
