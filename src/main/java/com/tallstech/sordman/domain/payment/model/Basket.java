package com.tallstech.sordman.domain.payment.model;

import lombok.Data;

@Data
public class Basket {
    private String productItemName;
    private String basePriceOfProductItem;
    private int productItemCount;
}
