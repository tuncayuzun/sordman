package com.tallstech.sordman.exception;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;


@RequiredArgsConstructor
public class SordmanException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String SORDMAN_UNEXPECTED_ERROR = "An unexpected error occurred! Please contact the api support!";

    @Getter
    private final String message;

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String detail;

    @Getter
    private final HttpStatus status;

    public SordmanException(HttpStatus errorCode, Throwable cause) {
        super(errorCode.name(), cause);
        this.status = errorCode;
        this.message = cause.getMessage();
        this.detail = !StringUtils.isEmpty(cause.getMessage()) ? cause.getMessage() : SORDMAN_UNEXPECTED_ERROR;
    }


    public SordmanException(HttpStatus errorCode, String message) {
        super(errorCode.name());
        this.status = errorCode;
        this.message = message;
        this.detail = null;
    }


    public SordmanException(HttpStatus errorCode,String message, String errorDetail) {
        super(errorCode.name());
        this.status = errorCode;
        this.message = message;
        this.detail = errorDetail;
    }
}
