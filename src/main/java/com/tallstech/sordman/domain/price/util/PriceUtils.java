package com.tallstech.sordman.domain.price.util;

import static com.tallstech.sordman.constant.SordmanConstants.TYPE_RATE;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;


public final class PriceUtils {

    private PriceUtils() {}

    public static BigDecimal calculateSalesPrice(BigDecimal basePrice, String taxType, int taxAmount) {
        if (StringUtils.isEmpty(taxType) || taxAmount == 0) {
            return basePrice;
        } else {
            if (taxType.equalsIgnoreCase(TYPE_RATE)) {
                var amount = BigDecimal.valueOf(taxAmount);
                BigDecimal taxPrice = basePrice.multiply(amount).divide(BigDecimal.valueOf(100));
                return basePrice.add(taxPrice);
            } else {
                return basePrice.add(BigDecimal.valueOf(taxAmount)).stripTrailingZeros();
            }
        }
    }
}
