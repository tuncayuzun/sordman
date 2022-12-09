package com.tallstech.sordman.domain.shoppingcart.mapper;

import com.tallstech.sordman.domain.base.BaseMapper;
import com.tallstech.sordman.domain.base.type.CartItemType;
import com.tallstech.sordman.domain.base.type.TaxType;
import com.tallstech.sordman.domain.price.dto.TaxTypeDto;
import com.tallstech.sordman.model.PriceInfo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import com.tallstech.sordman.domain.shoppingcart.document.ShoppingCart;
import com.tallstech.sordman.domain.shoppingcart.dto.CartItemTypeDto;
import com.tallstech.sordman.domain.shoppingcart.dto.PriceInfoDto;
import com.tallstech.sordman.domain.shoppingcart.dto.ShoppingCartCreateDto;
import com.tallstech.sordman.domain.shoppingcart.dto.ShoppingCartDto;


@Mapper(componentModel = "spring")
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart, ShoppingCartDto> {

    ShoppingCart toDoc(ShoppingCartCreateDto shoppingCartCreateDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toDocForPatch(ShoppingCartDto shoppingCartDto, @MappingTarget ShoppingCart shoppingCart);

    CartItemType toDocType(CartItemTypeDto cartItemTypeDto);
    CartItemTypeDto toDtoType(CartItemType cartItemType);

    TaxType toDocType(TaxTypeDto taxTypeDto);
    TaxTypeDto toDtoType(TaxType taxType);

    PriceInfo toDocType(PriceInfoDto priceInfoDto);
    PriceInfoDto toDtoType(PriceInfo priceInfo);
}
