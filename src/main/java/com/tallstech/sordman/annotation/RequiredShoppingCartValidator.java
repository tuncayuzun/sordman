package com.tallstech.sordman.annotation;


import static com.tallstech.sordman.constant.SordmanConstants.SALES_ORDER_ID_SHOPPING_CART;

import java.util.List;
import java.util.Objects;

import com.tallstech.sordman.domain.salesorder.dto.IdTypeDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RequiredShoppingCartValidator implements ConstraintValidator<RequiredShoppingCart, List<IdTypeDto>> {
    @Override
    public void initialize(RequiredShoppingCart constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<IdTypeDto> idTypeDtos, ConstraintValidatorContext constraintValidatorContext) {
        return idTypeDtos.stream().anyMatch(idTypeDto -> SALES_ORDER_ID_SHOPPING_CART.equalsIgnoreCase(idTypeDto.name())
                && Objects.nonNull(idTypeDto.value())
                && !idTypeDto.value().toString().trim().isEmpty()
                && !"null".equalsIgnoreCase(idTypeDto.value().toString().trim())
        );
    }
}