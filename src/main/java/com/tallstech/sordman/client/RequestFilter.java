package com.tallstech.sordman.client;

import static com.tallstech.sordman.client.SordmanHttpClientConstants.AUTHORIZATION_KEY;
import static com.tallstech.sordman.client.SordmanHttpClientConstants.BEARER_KEY;
import static com.tallstech.sordman.client.SordmanHttpClientConstants.CORRELATION_ID_DATE_FORMAT;
import static com.tallstech.sordman.client.SordmanHttpClientConstants.CORRELATION_ID_PREFIX;
import static com.tallstech.sordman.client.SordmanHttpClientConstants.TOKEN_KEY;
import static com.tallstech.sordman.client.SordmanHttpClientConstants.CORRELATION_ID;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;


@Component
public class RequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
            Enumeration<String> headerNames = httpRequest.getHeaderNames();
            if (headerNames != null) {
                String token = httpRequest.getHeader(AUTHORIZATION_KEY);
                MDC.put(TOKEN_KEY, BEARER_KEY + token);
            }
            MDC.put(CORRELATION_ID, generateCorrelationId());
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.remove(TOKEN_KEY);
            MDC.remove(CORRELATION_ID);
        }
    }

    private static String generateCorrelationId() {
        var formatter = DateTimeFormatter.ofPattern(CORRELATION_ID_DATE_FORMAT);
        var time = LocalDateTime.now();
        return CORRELATION_ID_PREFIX + time.format(formatter);
    }
}
