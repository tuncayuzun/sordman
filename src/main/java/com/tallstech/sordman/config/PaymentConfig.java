package com.tallstech.sordman.config;

import com.iyzipay.Options;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import com.tallstech.sordman.model.PaymentOptions;

import java.util.Arrays;

import static com.tallstech.sordman.constant.SordmanConstants.PAYMENT_OPTIONS;

@Getter
@Configuration
public class PaymentConfig {
    @Value("${payment.merchant.key}")
    private String merchantKey;
    @Value("${payment.merchant.secret}")
    private String merchantSecret;
    @Value("${payment.url.base}")
    private String merchantBaseUrl;
    @Value("${payment.url.callback}")
    private String callbackUrl;
    // @Value("${payment.installments.enabled}")
    // private List<Integer> enabledInstallments;

    @Bean(name = PAYMENT_OPTIONS)
    @Primary
    public PaymentOptions options() {
        var options = new Options();
        options.setApiKey(merchantKey);
        options.setSecretKey(merchantSecret);
        options.setBaseUrl(merchantBaseUrl);

        return PaymentOptions.builder()
                .callBackUrl(callbackUrl)
                .enabledInstallments(Arrays.asList(Integer.valueOf(1)))
                .options(options)
                .build();
    }


}
