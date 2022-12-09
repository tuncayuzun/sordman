package com.tallstech.sordman.domain.subscription.mapper;

import com.tallstech.sordman.domain.base.BaseMapper;
import com.tallstech.sordman.domain.base.type.IdType;
import com.tallstech.sordman.domain.subscription.dto.SubscriptionCreateDto;
import com.tallstech.sordman.domain.subscription.dto.SubscriptionDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import com.tallstech.sordman.domain.subscription.document.Subscription;
import com.tallstech.sordman.domain.subscription.dto.IdTypeDto;


@Mapper(componentModel = "spring")
public interface SubscriptionMapper extends BaseMapper<Subscription, SubscriptionDto> {
    Subscription toDoc(SubscriptionCreateDto subscriptionCreateDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toDocForPatch(SubscriptionDto subscriptionDto, @MappingTarget Subscription subscription);

    IdType toDocType(IdTypeDto idTypeDto);
    IdTypeDto toDtoType(IdType idType);

}
