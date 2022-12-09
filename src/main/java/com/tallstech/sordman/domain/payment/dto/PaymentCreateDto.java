package com.tallstech.sordman.domain.payment.dto;

import java.time.LocalDateTime;
import java.util.List;


public record PaymentCreateDto(
        String name,
        String type,
        String description,
        String status,
        String authorizationCode,
        String channel,
        LocalDateTime paymentDate,
        PayerTypeDto payer,
        AmountTypeDto amount,
        List<PaymentItemTypeDto> paymentItems) {
}
