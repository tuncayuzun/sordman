package com.tallstech.sordman.domain.payment.util;

import static java.text.NumberFormat.getCurrencyInstance;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Locale;

import com.tallstech.sordman.domain.salesorder.document.SalesOrder;
import org.apache.commons.lang3.ObjectUtils;


public final class PaymentUtils {

    public static BigDecimal calculateCheckoutPrice(SalesOrder salesOrder) {
        return salesOrder.getOrder().getPrice();
    }

    public static double formatPriceWithDecimals(double amount) {
        var decimalFormat = new DecimalFormat("0.00");
        return Double.parseDouble(decimalFormat.format(amount));
    }

    public static double formatPriceWithLocalCurrency(Locale locale, double amount) {
        if (ObjectUtils.isEmpty(locale)) {
            locale = new Locale("tr", "TR");
        }
        var numberFormat = getCurrencyInstance(locale);
        return Double.parseDouble(numberFormat.format(amount));
    }

    private PaymentUtils() {
    }
}
