package com.tallstech.sordman.domain.subscription.dto;

import java.time.LocalDateTime;
import java.util.List;


public record SubscriptionCreateDto(
        String id,
        Long ownerId,
        String type,
        String status,
        List<IdTypeDto> relations,
        LocalDateTime startTime,
        LocalDateTime endTime,
        LocalDateTime firstBillingDate,
        LocalDateTime nextBillingDate,
        int period) {}
