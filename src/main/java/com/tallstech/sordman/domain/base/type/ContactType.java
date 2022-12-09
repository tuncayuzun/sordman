package com.tallstech.sordman.domain.base.type;

import lombok.Data;

@Data
public class ContactType {
    private String email;
    private String phone;
    private AddressType address;
}
