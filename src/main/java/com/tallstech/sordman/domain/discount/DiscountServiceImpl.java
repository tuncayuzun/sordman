package com.tallstech.sordman.domain.discount;

import com.tallstech.sordman.domain.discount.document.Discount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tallstech.sordman.domain.base.SordmanServiceImpl;


@Service
public class DiscountServiceImpl extends SordmanServiceImpl<Discount> {

    private DiscountRepository discountRepository;

    @Autowired
    public DiscountServiceImpl(DiscountRepository discountRepository){
        super(discountRepository);
        this.discountRepository = discountRepository;
    }

    public Discount getByCode(String code){
        return discountRepository.findByCode(code);
    }
}
