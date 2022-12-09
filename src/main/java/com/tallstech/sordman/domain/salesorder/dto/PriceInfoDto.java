package com.tallstech.sordman.domain.salesorder.dto;

import java.math.BigDecimal;


public record PriceInfoDto(
        int taxAmount,
        BigDecimal taxFreePrice,
        BigDecimal actualPrice,
        BigDecimal salesPrice,
        BigDecimal discountPrice) {
}
