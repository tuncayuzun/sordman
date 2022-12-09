package com.tallstech.sordman.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriceInfo {
    private int taxAmount;
    private BigDecimal taxFreePrice;
    private BigDecimal actualPrice;
    private BigDecimal salesPrice;
    private BigDecimal discountPrice = BigDecimal.ZERO;
}
