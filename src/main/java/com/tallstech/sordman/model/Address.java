package com.tallstech.sordman.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class Address {
    private String contactName;
    private String country;
    private String city;
    private String district;
    private String detail;
    private String phone;
    private String postalCode;
    private Long accountId;
    private Long customerId;
}
