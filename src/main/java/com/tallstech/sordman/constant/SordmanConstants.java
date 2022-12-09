package com.tallstech.sordman.constant;

public final class SordmanConstants {
    private SordmanConstants() {
    }

    /** Configs **/
    public static final String MONGO_TEMPLATE = "sordmanMongoTemplate";
    /* Names */
    public static final String SORDMAN_OBJECT_MAPPER = "sordmanObjectMapper";
    public static final String SORDMAN_HTTP_CLIENT = "sordmanHttpRestClient";

    /* Common */
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String TYPE_INDIVIDUAL = "individual";
    public static final String TYPE_ORGANISATION = "organisation";
    public static final String TYPE_IDENTIFICATION = "identification";
    public static final String TYPE_IDENTIFICATION_TCKN = "tckn";
    public static final String TYPE_CONTACT = "contact";
    public static final String TYPE_CONTACT_PHONE = "phone";
    public static final String TYPE_CONTACT_EMAIL = "email";
    public static final String TYPE_RATE = "rate";

    /* PAYMENT */
    public static final String PAYMENT_PREFIX = "payment-";
    public static final String PAYMENT_OPTIONS = "signOrderPaymentOptions";

    /* SALES ORDER */
    public static final String  SALES_ORDER_ID_SHOPPING_CART = "shoppingCartId";
    public static final String  SALES_ORDER_ID_CUSTOMER = "customerId";
    public static final String  SALES_ORDER_ID_ACCOUNT = "accountId";
    public static final String  SALES_ORDER_STATUS_CREATED = "CREATED";

    /* PRODUCT CATALOG */
    public static final String  PRODUCT_CATALOG_ID_PRICE = "priceId";
    public static final String  PRODUCT_CATALOG_ID_DISCOUNT = "discountId";

    /* SHOPPING CART */
    public static final String  CART_STATUS_OPEN = "open";
    public static final String  CART_STATUS_CLOSE = "close";


}
