package com.tallstech.sordman.domain.salesorder.dto;

import java.time.LocalDateTime;


public record IndividualTypeDto(
        Long id,
        String firstName,
        String familyName,
        LocalDateTime dateOfBirth,
        String dateOfPlace,
        String gender,
        String role,
        IdentificationTypeDto identification,
        ContactTypeDto contact,
        BillingInfoTypeDto billingInfo) {}
