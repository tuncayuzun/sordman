package com.tallstech.sordman.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RequiredShoppingCartValidator.class)
public @interface RequiredShoppingCart {
    String message() default "List must contain at shopping cart id!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}