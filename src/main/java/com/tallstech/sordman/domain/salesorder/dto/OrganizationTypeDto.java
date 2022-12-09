package com.tallstech.sordman.domain.salesorder.dto;

public record OrganizationTypeDto(
        Long id,
        String type,
        String name,
        String description,
        ContactTypeDto contact,
        ContactPersonTypeDto contactPerson,
        BillingInfoTypeDto billingInfo) {}
