package com.tallstech.sordman.domain.base.type;

import lombok.Data;

import java.util.List;

@Data
public class DescriptionType {
    private List<CharacteristicType> characteristics;
}
