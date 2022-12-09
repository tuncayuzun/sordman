package com.tallstech.sordman.util;

import com.tallstech.sordman.domain.shoppingcart.document.ShoppingCart;
import com.tallstech.sordman.exception.SordmanException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;

public final class ValidationUtils {

    private ValidationUtils() {
    }

    public static void validateShoppingCart(ShoppingCart shoppingCart) {
        if (ObjectUtils.isEmpty(shoppingCart)) {
            throw new SordmanException(HttpStatus.NOT_FOUND, "Shopping cart not found!");
        } else if (ObjectUtils.isEmpty(shoppingCart.getCartItems()) || shoppingCart.getCartItems().isEmpty() || ObjectUtils.isEmpty(shoppingCart.getTotalPrice())) {
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid shopping cart!");
        }
    }
}
