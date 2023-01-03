package com.tallstech.sordman.util;

import static org.junit.jupiter.api.Assertions.*;

import com.tallstech.sordman.domain.base.type.IdType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


class GeneratorTest {

    @Test
    @DisplayName("When getting displ")
    void generateIdType() {
        IdType response = Generator.generateIdType("id", "1234");
        assertAll("Results...",
                () -> assertNotNull(response),
                () -> assertEquals("1234", response.getValue())
        );

    }
}