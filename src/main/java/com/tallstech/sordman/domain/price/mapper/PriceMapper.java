package com.tallstech.sordman.domain.price.mapper;

import com.tallstech.sordman.domain.price.dto.PriceCreateDto;
import com.tallstech.sordman.domain.price.dto.PriceDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import com.tallstech.sordman.domain.base.BaseMapper;
import com.tallstech.sordman.domain.base.type.TaxType;
import com.tallstech.sordman.domain.price.document.Price;
import com.tallstech.sordman.domain.price.dto.TaxTypeDto;

@Mapper(componentModel = "spring")
public interface PriceMapper extends BaseMapper<Price, PriceDto> {

    Price toDoc(PriceCreateDto priceCreateDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toDocForPatch(PriceDto priceDto, @MappingTarget Price price);

    TaxType toDocType(TaxTypeDto taxTypeDto);
    TaxTypeDto toDtoType(TaxType taxType);
}
