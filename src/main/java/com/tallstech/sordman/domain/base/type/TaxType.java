package com.tallstech.sordman.domain.base.type;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
public class TaxType {
    private String country;
    @NotNull
    private String type;
    @NotNull
    @Positive
    private int amount;
}
