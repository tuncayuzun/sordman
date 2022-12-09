package com.tallstech.sordman.domain.base.type;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RelatedPartyType {
    private String type;
    private IndividualType individual = null;
    private OrganizationType organization = null;
}
