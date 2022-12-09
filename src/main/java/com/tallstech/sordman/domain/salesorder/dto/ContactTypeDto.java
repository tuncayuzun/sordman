package com.tallstech.sordman.domain.salesorder.dto;

public record ContactTypeDto(
        String email,
        String phone,
        AddressTypeDto address) {}
