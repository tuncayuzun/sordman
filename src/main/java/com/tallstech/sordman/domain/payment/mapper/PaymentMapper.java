package com.tallstech.sordman.domain.payment.mapper;

import com.tallstech.sordman.domain.base.BaseMapper;
import com.tallstech.sordman.domain.base.type.AmountType;
import com.tallstech.sordman.domain.base.type.CharacteristicType;
import com.tallstech.sordman.domain.base.type.PayerType;
import com.tallstech.sordman.domain.base.type.PaymentItemType;
import com.tallstech.sordman.domain.payment.document.Payment;
import com.tallstech.sordman.domain.payment.dto.AmountTypeDto;
import com.tallstech.sordman.domain.payment.dto.PayerTypeDto;
import com.tallstech.sordman.domain.payment.dto.PaymentCreateDto;
import com.tallstech.sordman.domain.payment.dto.PaymentDto;
import com.tallstech.sordman.domain.payment.dto.PaymentItemTypeDto;
import com.tallstech.sordman.domain.productcatalog.dto.CharacteristicTypeDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PaymentMapper extends BaseMapper<Payment, PaymentDto> {
    Payment toDoc(PaymentCreateDto paymentCreateDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toDocForPatch(PaymentDto paymentDto, @MappingTarget Payment payment);

    CharacteristicType toDocType(CharacteristicTypeDto characteristicTypeDto);
    CharacteristicTypeDto toDtoType(CharacteristicType characteristicType);

    AmountType toDocType(AmountTypeDto amountTypeDto);
    AmountTypeDto toDtoType(AmountTypeDto amountType);

    PayerType toDocType(PayerTypeDto payerTypeDto);
    PayerTypeDto toDtoType(PayerType payerType);

    PaymentItemType toDocType(PaymentItemTypeDto paymentItemTypeDto);
    PaymentItemTypeDto toDtoType(PaymentItemType paymentItemType);
}
