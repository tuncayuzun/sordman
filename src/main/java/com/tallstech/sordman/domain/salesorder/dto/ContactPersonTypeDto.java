package com.tallstech.sordman.domain.salesorder.dto;

import java.time.LocalDateTime;

public record ContactPersonTypeDto(
        String firstName,
        String familyName,
        LocalDateTime dateOfBirth,
        String dateOfPlace,
        String gender,
        String role,
        ContactPointTypeDto contactPoint) {}
