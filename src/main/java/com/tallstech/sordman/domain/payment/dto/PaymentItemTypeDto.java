package com.tallstech.sordman.domain.payment.dto;

import lombok.Data;


public record PaymentItemTypeDto(
        AmountTypeDto amount,
        CharacteristicTypeDto item) {
}
