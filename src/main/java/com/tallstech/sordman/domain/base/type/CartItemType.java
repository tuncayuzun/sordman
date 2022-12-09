package com.tallstech.sordman.domain.base.type;

import lombok.Data;
import com.tallstech.sordman.model.PriceInfo;

import java.math.BigDecimal;

@Data
public class CartItemType {
    private String catalogId;
    private String code;
    private String name;
    private String type;
    private String category;
    private String subCategory;
    private PriceInfo price;
    private int quantity;
    private BigDecimal totalPrice;
}
