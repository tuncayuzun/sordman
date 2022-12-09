package com.tallstech.sordman.domain.base.type;

import lombok.Data;

@Data
public class OrganizationType {
    private Long id;
    private String type;
    private String name;
    private String description;
    private ContactType contact;
    private ContactPersonType contactPerson;
    private BillingInfoType billingInfo;

}
