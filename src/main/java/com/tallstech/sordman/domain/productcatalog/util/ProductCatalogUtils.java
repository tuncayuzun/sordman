package com.tallstech.sordman.domain.productcatalog.util;

import static com.tallstech.sordman.constant.SordmanConstants.TYPE_RATE;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.tallstech.sordman.domain.base.type.IdType;
import com.tallstech.sordman.domain.discount.document.Discount;
import com.tallstech.sordman.domain.price.document.Price;
import com.tallstech.sordman.domain.productcatalog.document.ProductCatalog;
import com.tallstech.sordman.exception.SordmanException;
import com.tallstech.sordman.model.PriceInfo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;


public final class ProductCatalogUtils {

    private ProductCatalogUtils() {
    }

    public static Object getValueFromProductCatalogRelations(ProductCatalog productCatalog, String requestedId) {
        Object requestedIdValue = null;
        if (ObjectUtils.isNotEmpty(productCatalog)
                && ObjectUtils.isNotEmpty(productCatalog.getRelations())
                && !productCatalog.getRelations().isEmpty()
                && ObjectUtils.isNotEmpty(requestedId)) {

            requestedIdValue = productCatalog.getRelations().stream()
                    .filter(item -> requestedId.equalsIgnoreCase(item.getName()))
                    .map(IdType::getValue)
                    .findFirst()
                    .orElse(null);
        }

        return requestedIdValue;
    }

    public static PriceInfo calculateCurrentPriceOfProductCatalogItem(Price price, Discount discount) {
        if (ObjectUtils.isEmpty(price)) {
            throw new SordmanException(HttpStatus.NOT_FOUND, "Price not found!");
        } else {
            var priceInfo = new PriceInfo();
            priceInfo.setTaxAmount(price.getTax().getAmount());
            priceInfo.setTaxFreePrice(price.getBasePrice());
            priceInfo.setActualPrice(price.getSalesPrice());

            if (ObjectUtils.isEmpty(discount) && discount.getValidityPeriod().isAfter(LocalDateTime.now())) {
                if (TYPE_RATE.equalsIgnoreCase(discount.getType())) {
                    var discountAmount = BigDecimal.valueOf(discount.getAmount());
                    BigDecimal discountPrice = price.getSalesPrice().multiply(discountAmount).divide(BigDecimal.valueOf(100));
                    priceInfo.setDiscountPrice(discountPrice);
                    priceInfo.setSalesPrice(price.getSalesPrice().subtract(discountPrice));
                } else {
                    priceInfo.setDiscountPrice(BigDecimal.valueOf(discount.getAmount()));
                    priceInfo.setSalesPrice(price.getSalesPrice().subtract(BigDecimal.valueOf(discount.getAmount())));
                }
            } else {
                priceInfo.setSalesPrice(price.getSalesPrice());
            }
            return priceInfo;
        }
    }
}
