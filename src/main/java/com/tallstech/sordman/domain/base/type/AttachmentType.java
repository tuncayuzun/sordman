package com.tallstech.sordman.domain.base.type;

import lombok.Data;

import java.util.List;

@Data
public class AttachmentType {
    private List<CharacteristicType> characteristics;
}
