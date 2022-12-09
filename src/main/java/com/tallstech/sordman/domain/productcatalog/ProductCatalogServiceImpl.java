package com.tallstech.sordman.domain.productcatalog;

import com.tallstech.sordman.constant.SordmanConstants;
import com.tallstech.sordman.domain.base.SordmanServiceImpl;
import com.tallstech.sordman.domain.discount.DiscountServiceImpl;
import com.tallstech.sordman.domain.discount.document.Discount;
import com.tallstech.sordman.domain.price.PriceServiceImpl;
import com.tallstech.sordman.domain.price.document.Price;
import com.tallstech.sordman.domain.productcatalog.document.ProductCatalog;
import com.tallstech.sordman.domain.productcatalog.util.ProductCatalogUtils;
import com.tallstech.sordman.model.PriceInfo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductCatalogServiceImpl extends SordmanServiceImpl<ProductCatalog> {

    private PriceServiceImpl priceService;
    private DiscountServiceImpl discountService;

    @Autowired
    public ProductCatalogServiceImpl(ProductCatalogRepository productCatalogRepository, PriceServiceImpl priceService, DiscountServiceImpl discountService) {
        super(productCatalogRepository);
        this.priceService = priceService;
        this.discountService = discountService;
    }

    @Override
    public ProductCatalog getById(String id) {
        var productCatalog = super.getById(id);
        if (ObjectUtils.isNotEmpty(productCatalog) && ObjectUtils.isNotEmpty(productCatalog.getRelations()) && !productCatalog.getRelations().isEmpty()) {
            fetchCatalogItemWithCurrentPrice(productCatalog);
        }
        return productCatalog;
    }

    public List<ProductCatalog> getAllCatalogItemsWithCurrentPrice() {
        List<ProductCatalog> productCatalogList = super.getAll();
        for (ProductCatalog productCatalog : productCatalogList) {
            fetchCatalogItemWithCurrentPrice(productCatalog);
        }
        return productCatalogList;
    }

    private void fetchCatalogItemWithCurrentPrice(ProductCatalog productCatalog) {
        PriceInfo priceInfo;
        if (ObjectUtils.isNotEmpty(productCatalog)) {
            priceInfo = calculateCurrentPrice(productCatalog);
            productCatalog.setPrice(priceInfo);
        }
    }

    public PriceInfo getCatalogItemCurrentPrice(String productCatalogId) {
        ProductCatalog productCatalog = getById(productCatalogId);
        PriceInfo priceInfo = null;
        if (ObjectUtils.isNotEmpty(productCatalog)) {
            priceInfo = calculateCurrentPrice(productCatalog);
        }
        return priceInfo;
    }

    private PriceInfo calculateCurrentPrice(ProductCatalog productCatalog) {
        Price price = null;
        String priceId = (String) ProductCatalogUtils.getValueFromProductCatalogRelations(productCatalog, SordmanConstants.PRODUCT_CATALOG_ID_PRICE);
        if (ObjectUtils.isNotEmpty(priceId)) {
            price = priceService.getById(priceId);
        }

        Discount discount = null;
        String discountId = (String) ProductCatalogUtils.getValueFromProductCatalogRelations(productCatalog, SordmanConstants.PRODUCT_CATALOG_ID_DISCOUNT);
        if (ObjectUtils.isNotEmpty(discountId)) {
            discount = discountService.getById(discountId);
        }
        return ProductCatalogUtils.calculateCurrentPriceOfProductCatalogItem(price, discount);
    }
}
