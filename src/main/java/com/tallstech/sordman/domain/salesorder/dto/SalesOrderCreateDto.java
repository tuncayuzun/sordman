package com.tallstech.sordman.domain.salesorder.dto;

import java.util.List;

import com.tallstech.sordman.annotation.RequiredShoppingCart;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record SalesOrderCreateDto(
        @NotNull
        @NotBlank
        String partyId,
        String type,
        String channel,
        @NotNull(message = "Must contain at least shopping cart id!")
        @RequiredShoppingCart
        List<IdTypeDto> relations,
        RelatedPartyTypeDto relatedParty,
        OrderItemTypeDto order,
        AttachmentTypeDto attachment) {}
