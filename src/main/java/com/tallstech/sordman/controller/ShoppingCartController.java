package com.tallstech.sordman.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.tallstech.sordman.api.ShoppingCartApi;
import com.tallstech.sordman.domain.base.type.CartItemType;
import com.tallstech.sordman.domain.discount.DiscountServiceImpl;
import com.tallstech.sordman.domain.discount.document.Discount;
import com.tallstech.sordman.domain.shoppingcart.ShoppingCartServiceImpl;
import com.tallstech.sordman.domain.shoppingcart.document.ShoppingCart;
import com.tallstech.sordman.domain.shoppingcart.dto.CartItemTypeDto;
import com.tallstech.sordman.domain.shoppingcart.dto.ShoppingCartCreateDto;
import com.tallstech.sordman.domain.shoppingcart.dto.ShoppingCartDto;
import com.tallstech.sordman.domain.shoppingcart.mapper.ShoppingCartMapper;
import com.tallstech.sordman.exception.SordmanException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class ShoppingCartController implements ShoppingCartApi {

    private ShoppingCartMapper shoppingCartMapper;
    private ShoppingCartServiceImpl shoppingCartService;
    private DiscountServiceImpl discountService;

    @Autowired
    public ShoppingCartController(ShoppingCartMapper shoppingCartMapper, ShoppingCartServiceImpl shoppingCartService, DiscountServiceImpl discountService) {
        this.shoppingCartMapper = shoppingCartMapper;
        this.shoppingCartService = shoppingCartService;
        this.discountService = discountService;
    }

    @Override
    public ResponseEntity<Object> getShoppingCartById(Map<String, String> header, String id) {
        var shoppingCart = shoppingCartService.getById(id);
        var shoppingCartDto = shoppingCartMapper.toDto(shoppingCart);
        return ResponseEntity.ok(shoppingCartDto);
    }

    @Override
    public ResponseEntity<Object> getActiveShoppingCartByPartyId(Map<String, String> header, Long id) {
        var shoppingCart = shoppingCartService.findActiveShoppingCartByPartyId(id);
        var shoppingCartDto = shoppingCartMapper.toDto(shoppingCart);
        return ResponseEntity.ok(shoppingCartDto);
    }

    @Override
    public ResponseEntity<Object> getAllShoppingCarts(Map<String, String> header, int page, int size, String[] sortBy) {
        Page<ShoppingCartDto> pageOfShoppingCarts = shoppingCartService.getAllPaginated(page, size, sortBy)
                .map(shoppingCart -> shoppingCartMapper.toDto(shoppingCart));
        return ResponseEntity.ok(pageOfShoppingCarts);
    }

    @Override
    public ResponseEntity<Object> createShoppingCart(Map<String, String> header, ShoppingCartCreateDto shoppingCartCreateDto) {
        var shoppingCart = shoppingCartMapper.toDoc(shoppingCartCreateDto);
        var shoppingCartDto = shoppingCartMapper.toDto(shoppingCartService.create(shoppingCart));
        return ResponseEntity.ok(shoppingCartDto);
    }

    @Override
    public ResponseEntity<Object> addItemToShoppingCart(Map<String, String> header, String id, CartItemTypeDto cartItemTypeDto) {
        CartItemType cartItem = shoppingCartMapper.toDocType(cartItemTypeDto);
        return ResponseEntity.ok(shoppingCartService.addItemToShoppingCart(id, cartItem));
    }

    @Override
    public ResponseEntity<Object> removeItemFromShoppingCart(Map<String, String> header, String id, CartItemTypeDto cartItemTypeDto) {
        CartItemType cartItem = shoppingCartMapper.toDocType(cartItemTypeDto);
        return ResponseEntity.ok(shoppingCartService.removeItemFromShoppingCart(id, cartItem));
    }

    //TODO: Will be refactored
    @Override
    public ResponseEntity<Object> addCouponToShoppingCart(Map<String, String> header, String shoppingCartId, String couponCode) {
        Discount discount = null;
        try {
            discount = discountService.getByCode(couponCode);
        } catch (Exception exception) {
            log.error("No discount found with coupon code {} !", couponCode);
        }

        if (ObjectUtils.isNotEmpty(discount) && discount.getValidityPeriod().isAfter(LocalDateTime.now())) {
            var shoppingCartDto = shoppingCartMapper.toDto(shoppingCartService.addCouponToShoppingCart(shoppingCartId, discount));
            return ResponseEntity.ok(shoppingCartDto);
        } else {
            //TODO: Will retun exception if coupon expired
            var shoppingCart = shoppingCartService.getById(shoppingCartId);
            var shoppingCartDto = shoppingCartMapper.toDto(shoppingCart);
            ShoppingCartDto shoppingCartResponse = shoppingCartDto;
            return ResponseEntity.ok(shoppingCartResponse);
        }
    }

    @Override
    public ResponseEntity<Object> updateShoppingCart(Map<String, String> header, ShoppingCartDto shoppingCartDto) {
        var shoppingCart = shoppingCartService.getById(shoppingCartDto.id());
        shoppingCartMapper.toDocForPatch(shoppingCartDto, shoppingCart);
        return ResponseEntity.ok(shoppingCartService.update(shoppingCart));
    }

    @Override
    public ResponseEntity<Object> bulkUpdateShoppingCart(Map<String, String> header, List<ShoppingCartDto> shoppingCartDtoList, String field, Object value) throws SordmanException {
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.toDocList(shoppingCartDtoList);
        return ResponseEntity.ok(shoppingCartService.bulkUpdate(shoppingCartList, field, value));
    }

    @Override
    public ResponseEntity<Object> deleteShoppingCart(Map<String, String> header, String id) {
        return ResponseEntity.ok(shoppingCartService.delete(id));
    }

    @Override
    public ResponseEntity<Object> bulkDeleteShoppingCart(Map<String, String> header, List<String> idList) throws SordmanException {
        return ResponseEntity.ok(shoppingCartService.bulkDelete(idList));
    }
}
