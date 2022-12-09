package com.tallstech.sordman.annotation;

import static com.tallstech.sordman.constant.SordmanConstants.PRODUCT_CATALOG_ID_PRICE;

import java.util.List;
import java.util.Objects;

import com.tallstech.sordman.domain.productcatalog.dto.IdTypeDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RequiredPriceValidator implements ConstraintValidator<RequiredPrice, List<IdTypeDto>> {
    @Override
    public void initialize(RequiredPrice constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<IdTypeDto> idTypeDtos, ConstraintValidatorContext constraintValidatorContext) {
        return idTypeDtos.stream().anyMatch(idTypeDto -> PRODUCT_CATALOG_ID_PRICE.equalsIgnoreCase(idTypeDto.name())
                && Objects.nonNull(idTypeDto.value())
                && !idTypeDto.value().toString().trim().isEmpty()
                && !"null".equalsIgnoreCase(idTypeDto.value().toString().trim())
        );
    }
}
