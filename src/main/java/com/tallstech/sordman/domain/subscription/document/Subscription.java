package com.tallstech.sordman.domain.subscription.document;

import com.tallstech.sordman.domain.base.BaseDocument;
import com.tallstech.sordman.domain.base.type.IdType;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
public class Subscription extends BaseDocument {
    private Long ownerId;
    private String type;
    private String status;
    private List<IdType> relations;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime firstBillingDate;
    private LocalDateTime nextBillingDate;
    private int period;
}
