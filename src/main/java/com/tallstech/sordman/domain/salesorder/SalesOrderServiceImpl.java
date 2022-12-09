package com.tallstech.sordman.domain.salesorder;

import static com.tallstech.sordman.constant.SordmanConstants.SALES_ORDER_ID_ACCOUNT;
import static com.tallstech.sordman.constant.SordmanConstants.SALES_ORDER_ID_CUSTOMER;
import static com.tallstech.sordman.constant.SordmanConstants.SALES_ORDER_STATUS_CREATED;
import static com.tallstech.sordman.constant.SordmanConstants.TYPE_INDIVIDUAL;
import static com.tallstech.sordman.constant.SordmanConstants.TYPE_ORGANISATION;
import static com.tallstech.sordman.domain.salesorder.util.SalesOrderUtils.generateTempCustomer;
import static com.tallstech.sordman.util.Generator.generateIdType;
import static com.tallstech.sordman.util.Generator.generateIndividual;
import static com.tallstech.sordman.util.Generator.generateOrganization;
import static com.tallstech.sordman.util.ValidationUtils.validateShoppingCart;

import java.util.Map;

import com.tallstech.sordman.client.SordmanHttpClient;
import com.tallstech.sordman.config.SordmanConfig;
import com.tallstech.sordman.domain.base.SordmanRepository;
import com.tallstech.sordman.domain.base.SordmanServiceImpl;
import com.tallstech.sordman.domain.base.type.CartItemType;
import com.tallstech.sordman.domain.base.type.OrderItemType;
import com.tallstech.sordman.domain.base.type.RelatedPartyType;
import com.tallstech.sordman.domain.salesorder.document.SalesOrder;
import com.tallstech.sordman.domain.shoppingcart.ShoppingCartServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;


@Service
public class SalesOrderServiceImpl extends SordmanServiceImpl<SalesOrder> {

    private SordmanConfig sordmanConfig;
    private SordmanHttpClient sordmanHttpClient;
    private ShoppingCartServiceImpl shoppingCartService;

    public SalesOrderServiceImpl(SordmanRepository<SalesOrder> sordmanRepository, SordmanConfig sordmanConfig, SordmanHttpClient sordmanHttpClient, ShoppingCartServiceImpl shoppingCartService) {
        super(sordmanRepository);
        this.sordmanConfig = sordmanConfig;
        this.sordmanHttpClient = sordmanHttpClient;
        this.shoppingCartService = shoppingCartService;
    }


    public SalesOrder createSalesOrder(SalesOrder salesOrder, Map<String, String> header) {
        /* TODO Will be uncommented  after user service update
        Customer response = SordmanHttpClient.doGet(signOrderConfig.getGetCustomerUrl(), header, Customer.class, SignResponse.class);
        validateCustomerResponse(response);
        var customer = response.getPayload();
         */

        // TODO: Will be deleted
        var customer = generateTempCustomer();

        var shoppingCart = shoppingCartService.findActiveShoppingCartByPartyId(Long.valueOf(salesOrder.getPartyId()));
        validateShoppingCart(shoppingCart);

        var relatedParty = new RelatedPartyType();
        if (ObjectUtils.isEmpty(customer.getType()) || TYPE_INDIVIDUAL.equalsIgnoreCase(customer.getType())) {
            var individualType = generateIndividual(customer);
            relatedParty.setType(TYPE_INDIVIDUAL);
            relatedParty.setIndividual(individualType);
        } else {
            var organizationType = generateOrganization(customer);
            relatedParty.setType(TYPE_ORGANISATION);
            relatedParty.setOrganization(organizationType);
        }

        salesOrder.setRelatedParty(relatedParty);

        //TODO : will be refactored after customer update
        var address = customer.getAddresses().stream().findFirst().orElse(null);
        if (ObjectUtils.isNotEmpty(address)) {
            salesOrder.getRelations().add(generateIdType(SALES_ORDER_ID_ACCOUNT, address.getAccountId()));
            salesOrder.getRelations().add(generateIdType(SALES_ORDER_ID_CUSTOMER, address.getCustomerId()));
        }

        OrderItemType orderItemType = new OrderItemType();
        orderItemType.setItems(shoppingCart.getCartItems());

        for (CartItemType cartItem : shoppingCart.getCartItems()) {
            orderItemType.setPrice(orderItemType.getPrice().add(cartItem.getTotalPrice()));
        }

        salesOrder.setOrder(orderItemType);
        salesOrder.setStatus(SALES_ORDER_STATUS_CREATED);

        return create(salesOrder);
    }
}
