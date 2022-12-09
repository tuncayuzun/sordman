package com.tallstech.sordman.domain.discount.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DiscountDto(
    @NotNull
    String id,
    String code,
    String name,
    String status,
    String type,
    int amount,
    LocalDateTime validityPeriod){
}
