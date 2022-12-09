package com.tallstech.sordman.domain.discount.mapper;

import com.tallstech.sordman.domain.discount.document.Discount;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import com.tallstech.sordman.domain.base.BaseMapper;
import com.tallstech.sordman.domain.discount.dto.DiscountCreateDto;
import com.tallstech.sordman.domain.discount.dto.DiscountDto;

@Mapper(componentModel = "spring")
public interface DiscountMapper extends BaseMapper<Discount, DiscountDto> {

    Discount toDoc(DiscountCreateDto discountCreateDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toDocForPatch(DiscountDto discountDto, @MappingTarget Discount discount);
}
