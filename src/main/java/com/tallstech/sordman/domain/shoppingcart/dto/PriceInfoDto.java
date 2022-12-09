package com.tallstech.sordman.domain.shoppingcart.dto;

import java.math.BigDecimal;


public record PriceInfoDto(
        int taxAmount,
        BigDecimal taxFreePrice,
        BigDecimal actualPrice,
        BigDecimal salesPrice,
        BigDecimal discountPrice) {}
