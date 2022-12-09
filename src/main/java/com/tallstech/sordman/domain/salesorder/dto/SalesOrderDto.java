package com.tallstech.sordman.domain.salesorder.dto;

import java.util.List;


public record SalesOrderDto(
        String id,
        String partyId,
        String type,
        String status,
        String channel,
        List<IdTypeDto> relations,
        RelatedPartyTypeDto relatedParty,
        OrderItemTypeDto order,
        AttachmentTypeDto attachment) {}
