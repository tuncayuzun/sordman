package com.tallstech.sordman.domain.salesorder.document;

import com.tallstech.sordman.domain.base.BaseDocument;
import com.tallstech.sordman.domain.base.type.AttachmentType;
import com.tallstech.sordman.domain.base.type.IdType;
import com.tallstech.sordman.domain.base.type.OrderItemType;
import com.tallstech.sordman.domain.base.type.RelatedPartyType;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class SalesOrder extends BaseDocument {
    private String partyId;
    private String type;
    private String status;
    private String channel;
    private List<IdType> relations;
    private RelatedPartyType relatedParty;
    private OrderItemType order;
    private AttachmentType attachment;
}
