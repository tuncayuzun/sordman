package com.tallstech.sordman.domain.productcatalog.dto;

import java.util.List;

import com.tallstech.sordman.model.PriceInfo;
import org.springframework.data.annotation.Transient;


public record ProductCatalogDto(
        String id,
        String code,
        String name,
        String type,
        String category,
        String subCategory,
        String status,
        String defaultCurrency,
        List<IdTypeDto> relations,
        @Transient
        PriceInfo price,
        DescriptionTypeDto description,
        SalesTypeDto salesType,
        List<OfferingInfoTypeDto> offerings) {
}
