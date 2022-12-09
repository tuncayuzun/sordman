package com.tallstech.sordman.domain.salesorder.dto;

import java.util.List;

public record AttachmentTypeDto(
        List<CharacteristicTypeDto> characteristics
) {}
