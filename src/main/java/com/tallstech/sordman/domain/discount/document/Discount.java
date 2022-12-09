package com.tallstech.sordman.domain.discount.document;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import com.tallstech.sordman.domain.base.BaseDocument;

import java.time.LocalDateTime;

@Data
@Document
public class Discount  extends BaseDocument {
    private String code;
    private String name;
    private String status;
    private String type;
    private int amount;
    private LocalDateTime validityPeriod;
}
