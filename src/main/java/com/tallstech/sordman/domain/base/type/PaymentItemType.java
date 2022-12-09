package com.tallstech.sordman.domain.base.type;

import lombok.Data;

@Data
public class PaymentItemType {
    private AmountType amount;
    private CharacteristicType item;
}
