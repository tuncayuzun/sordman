package com.tallstech.sordman.model;

import com.iyzipay.Options;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentOptions {
    private String callBackUrl;
    private List<Integer> enabledInstallments;
    private Options options;


}
