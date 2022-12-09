package com.tallstech.sordman.domain.productcatalog.document;

import java.util.List;

import com.tallstech.sordman.domain.base.BaseDocument;
import com.tallstech.sordman.domain.base.type.DescriptionType;
import com.tallstech.sordman.domain.base.type.IdType;
import com.tallstech.sordman.domain.base.type.OfferingInfoType;
import com.tallstech.sordman.domain.base.type.SalesType;
import com.tallstech.sordman.model.PriceInfo;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class ProductCatalog extends BaseDocument {
    private String code;
    private String name;
    private String type;
    private String category;
    private String subCategory;
    private String status;
    private String defaultCurrency;
    private List<IdType> relations;
    @Transient
    private PriceInfo price;
    private DescriptionType description;
    private SalesType salesType;
    private List<OfferingInfoType> offerings;

}
