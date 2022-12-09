package com.tallstech.sordman.domain.price;

import com.tallstech.sordman.domain.price.document.Price;
import com.tallstech.sordman.domain.price.util.PriceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tallstech.sordman.domain.base.SordmanServiceImpl;


@Service
public class PriceServiceImpl extends SordmanServiceImpl<Price> {

    @Autowired
    public PriceServiceImpl(PriceRepository priceRepository){
        super(priceRepository);
    }

    @Override
    public Price create(Price price) {
        price.setSalesPrice(PriceUtils.calculateSalesPrice(price.getBasePrice(), price.getTax().getType(), price.getTax().getAmount()));
        return super.create(price);
    }
}
