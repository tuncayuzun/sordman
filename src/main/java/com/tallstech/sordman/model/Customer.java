package com.tallstech.sordman.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class Customer {
    private Long id;
    private LocalDateTime registrationDate;
    private LocalDateTime lastLoginDate;
    private String type;
    private String description;
    private String firstName;
    private String lastName;
    private Identification identification;
    private String email;
    private List<Address> addresses;
}
