package com.tallstech.sordman.domain.salesorder.dto;

public record AddressTypeDto(
        String type,
        String city,
        String county,
        String country,
        String postCode,
        String addressDetail) {}
