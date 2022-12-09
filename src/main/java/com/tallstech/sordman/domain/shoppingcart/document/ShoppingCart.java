package com.tallstech.sordman.domain.shoppingcart.document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tallstech.sordman.domain.base.BaseDocument;
import com.tallstech.sordman.domain.base.type.CartItemType;
import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document
@CompoundIndex(name = "shopping_cart_idx", def = "{'partyId':1, 'status':1}")
public class ShoppingCart extends BaseDocument {
    @JsonProperty("partyId")
    private Long partyId;
    @JsonProperty("status")
    private String status;
    @JsonProperty("cartItems")
    private List<CartItemType> cartItems;
    @JsonProperty("discount")
    private BigDecimal cartDiscount;
    @JsonProperty("totalPrice")
    private BigDecimal totalPrice;
    @JsonProperty("validityPeriod")
    private LocalDateTime validityPeriod;
}
