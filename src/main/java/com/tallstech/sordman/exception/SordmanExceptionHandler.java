package com.tallstech.sordman.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class SordmanExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus httpStatus, WebRequest webRequest) {
        final String path = webRequest.getDescription(false);

        List<String> validationErrors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + " : " + error.getDefaultMessage())
                .collect(Collectors.toList());

        SordmanError sordmanError = SordmanError.builder()
                .timestamp(LocalDateTime.now())
                .status(httpStatus.value())
                .message(httpStatus.name())
                .path(path)
                .detail(validationErrors.toString())
                .type(exception.getClass().getSimpleName())
                .build();

        return new ResponseEntity<>(sordmanError, httpStatus);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleException(WebRequest webRequest, Exception exception) {
        ResponseStatus responseStatus = exception.getClass().getAnnotation(ResponseStatus.class);
        final HttpStatus httpStatus = Objects.nonNull(responseStatus) ? responseStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR;
        final String localizedMessage = exception.getLocalizedMessage();
        final String path = webRequest.getDescription(false);
        final String message = Objects.nonNull(localizedMessage) ? localizedMessage : httpStatus.getReasonPhrase();

        SordmanError sordmanError = SordmanError.builder()
                .timestamp(LocalDateTime.now())
                .status(httpStatus.value())
                .message(httpStatus.name())
                .path(path)
                .detail(exception.getCause() != null ? message : "")
                .type(exception.getClass().getSimpleName())
                .build();

        return new ResponseEntity<>(sordmanError, httpStatus);
    }

    @ExceptionHandler(SordmanException.class)
    public ResponseEntity<Object> handleSordmanException(WebRequest webRequest, SordmanException sordmanException) {
        final String path = webRequest.getDescription(false);

        SordmanError sordmanError = SordmanError.builder()
                .timestamp(LocalDateTime.now())
                .status(sordmanException.getStatus().value())
                .message(sordmanException.getMessage())
                .path(path)
                .detail(sordmanException.getDetail())
                .type(sordmanException.getClass().getSimpleName())
                .build();

        return new ResponseEntity<>(sordmanError, sordmanException.getStatus());
    }
}
