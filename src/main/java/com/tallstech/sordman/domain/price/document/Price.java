package com.tallstech.sordman.domain.price.document;

import java.math.BigDecimal;

import com.tallstech.sordman.domain.base.BaseDocument;
import com.tallstech.sordman.domain.base.type.TaxType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
@CompoundIndex(name = "price_idx", def = "{'currency':1, 'status':1}")
public class Price extends BaseDocument {
    private String code;
    private String name;
    private String type;
    private String status;
    private BigDecimal basePrice;
    private BigDecimal salesPrice;
    private String currency;
    @NotNull
    private TaxType tax;
}
