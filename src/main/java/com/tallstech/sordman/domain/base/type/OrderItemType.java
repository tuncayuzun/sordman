package com.tallstech.sordman.domain.base.type;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderItemType {
    private BigDecimal price = BigDecimal.ZERO;
    private List<CartItemType> items;
}
