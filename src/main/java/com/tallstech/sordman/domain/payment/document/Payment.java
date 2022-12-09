package com.tallstech.sordman.domain.payment.document;

import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import com.tallstech.sordman.domain.base.BaseDocument;
import com.tallstech.sordman.domain.base.type.AmountType;
import com.tallstech.sordman.domain.base.type.PayerType;
import com.tallstech.sordman.domain.base.type.PaymentItemType;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
@CompoundIndex(name = "price_idx", def = "{'paymentDate':1, 'status':1}")
public class Payment extends BaseDocument {
    private String name;
    private String type;
    private String description;
    private String status;
    private String authorizationCode;
    private String channel;
    private LocalDateTime paymentDate;
    private PayerType payer;
    private AmountType amount;
    private List<PaymentItemType> paymentItems;
}
