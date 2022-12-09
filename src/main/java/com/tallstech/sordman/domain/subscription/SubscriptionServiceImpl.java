package com.tallstech.sordman.domain.subscription;

import com.tallstech.sordman.client.SordmanHttpClient;
import com.tallstech.sordman.config.SordmanConfig;
import com.tallstech.sordman.domain.base.SordmanRepository;
import com.tallstech.sordman.domain.base.SordmanServiceImpl;
import com.tallstech.sordman.domain.shoppingcart.ShoppingCartServiceImpl;
import com.tallstech.sordman.domain.subscription.document.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SubscriptionServiceImpl extends SordmanServiceImpl<Subscription> {

    private SordmanConfig sordmanConfig;
    private SordmanHttpClient sordmanHttpClient;

    @Autowired
    public SubscriptionServiceImpl(SordmanRepository<Subscription> sordmanRepository, SordmanHttpClient sordmanHttpClient, ShoppingCartServiceImpl shoppingCartService) {
        super(sordmanRepository);
        this.sordmanConfig = sordmanConfig;
        this.sordmanHttpClient = sordmanHttpClient;
    }

    /* TODO: Will be implemented after user service implementation
    public void notifyUser(String id, Map<String, String> header) {
        Subscription subscription = getById(id);
        SString response = SordmanHttpClient.doPost(signOrderConfig.getGetCustomerUrl(), header, String.class, SignResponse.class);
    }
     */
}
