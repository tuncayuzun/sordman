package com.tallstech.sordman.domain.base.type;

import lombok.Data;

@Data
public class CharacteristicType {
    private String name;
    private Object value;
    private String type;
    private String description;
}
