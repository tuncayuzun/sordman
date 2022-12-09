package com.tallstech.sordman.domain.salesorder.dto;

public record RelatedPartyTypeDto (
     String type,
     IndividualTypeDto individual,
     OrganizationTypeDto organization){}
