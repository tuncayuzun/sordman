package com.tallstech.sordman.domain.base.type;

import lombok.Data;

@Data
public class BillingInfoType {
    private IdentificationType identification;
    private AddressType address;
}
