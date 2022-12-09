package com.tallstech.sordman.domain.payment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PaymentTransaction {
    @JsonProperty("merchant_id")
    private String merchantId;
    @JsonProperty("merchant_key")
    private String merchantKey;
    @JsonProperty("merchant_salt")
    private String merchantSalt;
    @JsonProperty("emailstr")
    private String customerEmail;
    @JsonProperty("paytr_token")
    private String payerToken;
    @JsonProperty("payment_amountstr")
    private int amount;
    @JsonProperty("merchant_oid")
    private String orderId;
    @JsonProperty("user_namestr")
    private String customerName;
    @JsonProperty("user_addressstr")
    private String customerAddress;
    @JsonProperty("user_phonestr")
    private String customerPhone;
    @JsonProperty("merchant_ok_url")
    private String successUrl;
    @JsonProperty("merchant_fail_url")
    private String failUrl;
    @JsonProperty("user_ip")
    private String customerIpAddress;
    @JsonProperty("user_basket")
    private Object[][] basketItems;
    @JsonProperty("card_type")
    private String cardType;
    @JsonProperty("debug_on")
    private String debugMode;
    @JsonProperty("test_mode")
    private String testMode;
    @JsonProperty("non_3d")
    private String threeDMode;
    @JsonProperty("non3d_test_failed")
    private String threeDModeTest;
    @JsonProperty("installment_count")
    private int installmentCount;
    @JsonProperty("payment_type")
    private String paymentType;
    @JsonProperty("post_url")
    private String postUrl;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("cc_owner")
    private String cardOwner;
    @JsonProperty("card_number")
    private String cardNumber;
    @JsonProperty("expiry_month")
    private String expiryMonth;
    @JsonProperty("expiry_year")
    private String expiryYear;
    @JsonProperty("cvv")
    private String cvv;
}
