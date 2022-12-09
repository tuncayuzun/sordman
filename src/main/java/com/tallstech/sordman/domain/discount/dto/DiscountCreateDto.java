package com.tallstech.sordman.domain.discount.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;


@Validated
public record DiscountCreateDto(
    String code,
    @NotNull
    String name,
    String status,
    @NotNull
    String type,
    @NotNull
    int amount,
    LocalDateTime validityPeriod){
}
