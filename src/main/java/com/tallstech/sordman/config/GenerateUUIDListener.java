package com.tallstech.sordman.config;

import com.tallstech.sordman.domain.base.BaseDocument;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GenerateUUIDListener extends AbstractMongoEventListener<BaseDocument> {
    @Override
    public void onBeforeConvert(BeforeConvertEvent<BaseDocument> event) {
        var baseDocument = event.getSource();
        if (baseDocument.isNew()) {
            baseDocument.setId(UUID.randomUUID().toString());
        }
    }

}