package com.tallstech.sordman.domain.shoppingcart;

import java.math.BigDecimal;

import com.tallstech.sordman.domain.base.SordmanServiceImpl;
import com.tallstech.sordman.domain.base.type.CartItemType;
import com.tallstech.sordman.domain.discount.document.Discount;
import com.tallstech.sordman.domain.productcatalog.ProductCatalogServiceImpl;
import com.tallstech.sordman.domain.shoppingcart.document.ShoppingCart;
import com.tallstech.sordman.domain.shoppingcart.util.ShoppingCartUtils;
import com.tallstech.sordman.model.PriceInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ShoppingCartServiceImpl extends SordmanServiceImpl<ShoppingCart> {

    private ShoppingCartRepository shoppingCartRepository;
    private ProductCatalogServiceImpl productCatalogService;

    @Autowired
    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, ProductCatalogServiceImpl productCatalogService) {
        super(shoppingCartRepository);
        this.shoppingCartRepository = shoppingCartRepository;
        this.productCatalogService = productCatalogService;
    }

    public ShoppingCart findActiveShoppingCartByPartyId(Long partyId){
        ShoppingCart shoppingCart =  shoppingCartRepository.findActiveShoppingCartByPartyId(partyId);
        if(ObjectUtils.isNotEmpty(shoppingCart) && !shoppingCart.getCartItems().isEmpty()){
            for (CartItemType existingCartItem : shoppingCart.getCartItems()){
                PriceInfo priceInfo = productCatalogService.getCatalogItemCurrentPrice(existingCartItem.getCatalogId());
                if(ObjectUtils.isNotEmpty(priceInfo)  && existingCartItem.getPrice().getSalesPrice() != priceInfo.getSalesPrice()){
                    existingCartItem.setPrice(priceInfo);
                    BigDecimal currentTotalPrice = existingCartItem.getPrice().getSalesPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity()));
                    BigDecimal priceDifference = existingCartItem.getTotalPrice().subtract(currentTotalPrice);
                    existingCartItem.setTotalPrice(currentTotalPrice);
                    shoppingCart.setTotalPrice(shoppingCart.getTotalPrice().add(priceDifference));
                }
            }
        }
        return shoppingCart;
    }

    public ShoppingCart addItemToShoppingCart(String shoppingCartId, CartItemType cartItemType) {
        var shoppingCart = shoppingCartRepository.findById(shoppingCartId);
        ShoppingCartUtils.checkAndAddCartItemToShoppingCart(shoppingCart, cartItemType);
        return shoppingCartRepository.update(shoppingCart);
    }

    public ShoppingCart removeItemFromShoppingCart(String shoppingCartId, CartItemType cartItemType) {
        var shoppingCart = shoppingCartRepository.findById(shoppingCartId);
        ShoppingCartUtils.checkAndRemoveCartItemFromShoppingCart(shoppingCart, cartItemType);
        return shoppingCartRepository.update(shoppingCart);
    }

    public ShoppingCart addCouponToShoppingCart(String shoppingCartId, Discount discount) {
        var shoppingCart = shoppingCartRepository.findById(shoppingCartId);
        ShoppingCartUtils.validateAndAddCouponToShoppingCart(shoppingCart, discount);
        return shoppingCartRepository.update(shoppingCart);
    }

}
