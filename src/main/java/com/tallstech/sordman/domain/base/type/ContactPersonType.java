package com.tallstech.sordman.domain.base.type;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContactPersonType {
    private String firstName;
    private String familyName;
    private LocalDateTime dateOfBirth;
    private String dateOfPlace;
    private String gender;
    private String role;
    private ContactPointType contactPoint;
}
