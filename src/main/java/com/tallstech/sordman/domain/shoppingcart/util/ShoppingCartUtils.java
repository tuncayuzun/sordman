package com.tallstech.sordman.domain.shoppingcart.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.tallstech.sordman.constant.SordmanConstants;
import com.tallstech.sordman.domain.base.type.CartItemType;
import com.tallstech.sordman.domain.discount.document.Discount;
import com.tallstech.sordman.domain.shoppingcart.document.ShoppingCart;
import org.apache.commons.lang3.ObjectUtils;


public final class ShoppingCartUtils {

    private ShoppingCartUtils() {
    }

    public static LocalDateTime calculateValidityPeriod() {
        return LocalDateTime.now().plusMonths(1);
    }

    public static BigDecimal calculateShoppingCartTotalPrice(List<CartItemType> cartItemList) {
        var totalPrice = BigDecimal.valueOf(0.00);
        for (CartItemType cartItemType : cartItemList) {
            totalPrice = totalPrice.add(cartItemType.getTotalPrice());
        }
        return totalPrice;
    }

    public static void checkAndAddCartItemToShoppingCart(ShoppingCart shoppingCart, CartItemType cartItem) {
        boolean isExist = shoppingCart.getCartItems().stream()
                .anyMatch(item -> cartItem.getCatalogId().equals(item.getCatalogId()));

        if (isExist) {
            for (CartItemType cartItemType : shoppingCart.getCartItems()) {
                if (!shoppingCart.getCartItems().isEmpty() && cartItem.getCatalogId().equals(cartItemType.getCatalogId())) {
                    var totalQuantity = cartItemType.getQuantity() + cartItem.getQuantity();
                    cartItemType.setQuantity(totalQuantity);
                    cartItemType.setTotalPrice(cartItem.getPrice().getSalesPrice().multiply(BigDecimal.valueOf(totalQuantity)));
                }
            }
        } else {
            cartItem.setTotalPrice(cartItem.getPrice().getSalesPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            shoppingCart.getCartItems().add(cartItem);
        }

        var totalPrice = calculateShoppingCartTotalPrice(shoppingCart.getCartItems());
        shoppingCart.setTotalPrice(totalPrice);
    }

    public static void checkAndRemoveCartItemFromShoppingCart(ShoppingCart shoppingCart, CartItemType cartItem) {
        List<CartItemType> cartItemList = shoppingCart.getCartItems();

        CartItemType existingCartItem = shoppingCart.getCartItems().stream()
                .filter(item -> cartItem.getCatalogId().equals(item.getCatalogId()))
                .findFirst()
                .orElse(null);

        if (ObjectUtils.isNotEmpty(existingCartItem)) {
            for (CartItemType cartItemType : cartItemList) {
                if (existingCartItem.getCatalogId().equals(cartItemType.getCatalogId())) {
                    int remainingQuantity = existingCartItem.getQuantity() - cartItem.getQuantity();
                    if (remainingQuantity <= 0) {
                        shoppingCart.getCartItems().remove(existingCartItem);
                        break;
                    } else {
                        cartItemType.setQuantity(cartItemType.getQuantity() - cartItem.getQuantity());
                        cartItemType.setTotalPrice(cartItemType.getPrice().getSalesPrice().multiply(BigDecimal.valueOf(cartItemType.getQuantity())));
                    }
                }
            }

            var totalPrice = calculateShoppingCartTotalPrice(shoppingCart.getCartItems());
            shoppingCart.setTotalPrice(totalPrice);
        }
    }

    public static void validateAndAddCouponToShoppingCart(ShoppingCart shoppingCart, Discount discount) {
        if (SordmanConstants.TYPE_RATE.equalsIgnoreCase(discount.getType())) {
            var discountAmount = BigDecimal.valueOf(discount.getAmount());
            BigDecimal discountPrice = shoppingCart.getTotalPrice().multiply(discountAmount).divide(BigDecimal.valueOf(100));
            if (discountPrice.intValue() > 0) {
                shoppingCart.setCartDiscount(discountPrice);
            }
            BigDecimal totalPrice = shoppingCart.getTotalPrice().subtract(discountPrice);
            shoppingCart.setTotalPrice(totalPrice);
        } else {
            BigDecimal totalPrice = shoppingCart.getTotalPrice().subtract(BigDecimal.valueOf(discount.getAmount()));
            shoppingCart.setCartDiscount(BigDecimal.valueOf(discount.getAmount()));
            shoppingCart.setTotalPrice(totalPrice);
        }
    }
}
