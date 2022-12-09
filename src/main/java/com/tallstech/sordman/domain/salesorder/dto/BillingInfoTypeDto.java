package com.tallstech.sordman.domain.salesorder.dto;

public record BillingInfoTypeDto(
        IdentificationTypeDto identification,
        AddressTypeDto address) {}
