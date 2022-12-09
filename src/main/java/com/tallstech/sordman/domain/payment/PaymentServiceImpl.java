package com.tallstech.sordman.domain.payment;

import static com.tallstech.sordman.constant.SordmanConstants.DATE_TIME_PATTERN;
import static com.tallstech.sordman.constant.SordmanConstants.PAYMENT_OPTIONS;
import static com.tallstech.sordman.constant.SordmanConstants.PAYMENT_PREFIX;
import static com.tallstech.sordman.domain.salesorder.util.SalesOrderUtils.getValueFromSalesOrderRelations;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.iyzipay.model.Address;
import com.iyzipay.model.BasketItem;
import com.iyzipay.model.BasketItemType;
import com.iyzipay.model.Buyer;
import com.iyzipay.model.CheckoutFormInitialize;
import com.iyzipay.model.Currency;
import com.iyzipay.model.Locale;
import com.iyzipay.model.PaymentGroup;
import com.iyzipay.request.CreateCheckoutFormInitializeRequest;
import com.tallstech.sordman.client.SordmanHttpClient;
import com.tallstech.sordman.constant.SordmanConstants;
import com.tallstech.sordman.domain.base.SordmanServiceImpl;
import com.tallstech.sordman.domain.base.type.CartItemType;
import com.tallstech.sordman.domain.payment.document.Payment;
import com.tallstech.sordman.domain.payment.util.PaymentUtils;
import com.tallstech.sordman.domain.salesorder.SalesOrderServiceImpl;
import com.tallstech.sordman.domain.salesorder.document.SalesOrder;
import com.tallstech.sordman.domain.salesorder.util.SalesOrderUtils;
import com.tallstech.sordman.model.Customer;
import com.tallstech.sordman.model.PaymentOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class PaymentServiceImpl extends SordmanServiceImpl<Payment> {

    private PaymentOptions paymentOptions;
    private SordmanHttpClient sordmanHttpClient;
    private SalesOrderServiceImpl salesOrderService;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, SordmanHttpClient sordmanHttpClient, @Qualifier(PAYMENT_OPTIONS) PaymentOptions paymentOptions, SalesOrderServiceImpl salesOrderService) {
        super(paymentRepository);
        this.sordmanHttpClient = sordmanHttpClient;
        this.paymentOptions = paymentOptions;
        this.salesOrderService = salesOrderService;
    }

    public String createPaymentInitializeForm(String salesOrderId){

        SalesOrder salesOrder = salesOrderService.getById(salesOrderId);

        CreateCheckoutFormInitializeRequest request = new CreateCheckoutFormInitializeRequest();
        request.setLocale(Locale.TR.getValue());
        request.setConversationId(PAYMENT_PREFIX);

        BigDecimal price = PaymentUtils.calculateCheckoutPrice(salesOrder);
        request.setPrice(price);
        request.setPaidPrice(price);
        request.setCurrency(Currency.TRY.name());
        request.setBasketId((String) getValueFromSalesOrderRelations(salesOrder, SordmanConstants.SALES_ORDER_ID_SHOPPING_CART));
        request.setPaymentGroup(PaymentGroup.PRODUCT.name());
        request.setCallbackUrl(paymentOptions.getCallBackUrl());
        request.setEnabledInstallments(paymentOptions.getEnabledInstallments());

        Customer customer = SalesOrderUtils.generateTempCustomer();

        Buyer buyer = new Buyer();
        buyer.setId(customer.getId().toString());
        buyer.setName(customer.getFirstName());
        buyer.setSurname(customer.getLastName());
        buyer.setGsmNumber(customer.getAddresses().get(0).getPhone());
        buyer.setEmail(customer.getEmail());
        buyer.setIdentityNumber(customer.getIdentification().getValue());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        buyer.setLastLoginDate(customer.getLastLoginDate().format(formatter));
        buyer.setRegistrationDate(customer.getRegistrationDate().format(formatter));
        buyer.setRegistrationAddress(customer.getAddresses().get(0).getDetail());
        buyer.setIp("85.34.78.112");
        buyer.setCity(customer.getAddresses().get(0).getCity());
        buyer.setCountry(customer.getAddresses().get(0).getCountry());
        buyer.setZipCode(customer.getAddresses().get(0).getPostalCode());
        request.setBuyer(buyer);

        Address shippingAddress = new Address();
        shippingAddress.setContactName(customer.getFirstName()+" "+customer.getLastName());
        shippingAddress.setCity(customer.getAddresses().get(0).getCity());
        shippingAddress.setCountry(customer.getAddresses().get(0).getCountry());
        shippingAddress.setAddress(customer.getAddresses().get(0).getDetail());
        shippingAddress.setZipCode(customer.getAddresses().get(0).getPostalCode());
        request.setShippingAddress(shippingAddress);

        Address billingAddress = new Address();
        billingAddress.setContactName(customer.getFirstName()+" "+customer.getLastName());
        billingAddress.setCity(customer.getAddresses().get(0).getCity());
        billingAddress.setCountry(customer.getAddresses().get(0).getCountry());
        billingAddress.setAddress(customer.getAddresses().get(0).getDetail());
        billingAddress.setZipCode(customer.getAddresses().get(0).getPostalCode());
        request.setBillingAddress(billingAddress);

        List<BasketItem> basketItems = new ArrayList<>();
        for(CartItemType cartItem : salesOrder.getOrder().getItems()){
            BasketItem basketItem = new BasketItem();
            basketItem.setId(cartItem.getCatalogId());
            basketItem.setName(cartItem.getName());
            basketItem.setCategory1(cartItem.getCategory());
            basketItem.setCategory2(cartItem.getSubCategory());
            basketItem.setItemType(BasketItemType.VIRTUAL.name());
            basketItem.setPrice(cartItem.getTotalPrice());
            basketItems.add(basketItem);
        }
        request.setBasketItems(basketItems);


        CheckoutFormInitialize checkoutFormInitialize = CheckoutFormInitialize.create(request, paymentOptions.getOptions());
        return checkoutFormInitialize.getCheckoutFormContent();
    }

    public Payment createPaymentTransaction(Map<String, String> header){
        return null;
    }
}
