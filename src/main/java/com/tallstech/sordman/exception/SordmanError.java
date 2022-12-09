package com.tallstech.sordman.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;


@Builder
public record SordmanError(
        LocalDateTime timestamp,
        int status,
        String message,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String detail,
        String path,
        String type) {
}
