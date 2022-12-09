package com.tallstech.sordman.domain.base.type;

import lombok.Data;

@Data
public class AddressType {
    private String type;
    private String city;
    private String county;
    private String country;
    private String postCode;
    private String addressDetail;
}
