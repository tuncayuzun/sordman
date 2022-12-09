package com.tallstech.sordman.client;

public final class SordmanHttpClientConstants {
    private SordmanHttpClientConstants(){}

    /*Headers & Keys */
    public static final String X_FORWARDED_FOR_HEADER = "x-forwarded-for";
    public static final String CONTENT_TYPE_KEY = "Content-Type";
    public static final String CONTENT_TYPE_VALUE = "application/json";
    public static final String CORRELATION_ID = "correlationId";
    public static final String CORRELATION_ID_PREFIX = "sordman-";

    public static final String AUTHORIZATION_KEY = "Authorization";
    public static final String TOKEN_KEY = "token";
    public static final String BEARER_KEY = "Bearer ";

    /* Utils */
    public static final String CORRELATION_ID_DATE_FORMAT = "yyyyMMddHHmmssS";



}
