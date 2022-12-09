package com.tallstech.sordman.domain.payment.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentNotification {
    @JsonProperty("merchant_oid")
    private String orderId;
    @JsonProperty("status")
    private String status;
    @JsonProperty("total_amount")
    private String totalAmount;
    @JsonProperty("hash")
    private String hash;
    @JsonProperty("failed_reason_code")
    private String failReasonCode;
    @JsonProperty("failed_reason_msg")
    private String failedReasonMessage;
    @JsonProperty("test_mode")
    private String testMode;
    @JsonProperty("payment_type")
    private String paymentType;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("payment_amount")
    private String paymentAmount;
}

