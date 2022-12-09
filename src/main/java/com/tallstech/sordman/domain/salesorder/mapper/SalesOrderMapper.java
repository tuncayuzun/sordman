package com.tallstech.sordman.domain.salesorder.mapper;

import com.tallstech.sordman.domain.base.BaseMapper;
import com.tallstech.sordman.domain.base.type.AddressType;
import com.tallstech.sordman.domain.base.type.AttachmentType;
import com.tallstech.sordman.domain.base.type.BillingInfoType;
import com.tallstech.sordman.domain.base.type.CartItemType;
import com.tallstech.sordman.domain.base.type.CharacteristicType;
import com.tallstech.sordman.domain.base.type.ContactPersonType;
import com.tallstech.sordman.domain.base.type.ContactPointType;
import com.tallstech.sordman.domain.base.type.ContactType;
import com.tallstech.sordman.domain.base.type.IdType;
import com.tallstech.sordman.domain.base.type.IdentificationType;
import com.tallstech.sordman.domain.base.type.IndividualType;
import com.tallstech.sordman.domain.base.type.OrganizationType;
import com.tallstech.sordman.domain.base.type.RelatedPartyType;
import com.tallstech.sordman.domain.base.type.TaxType;
import com.tallstech.sordman.domain.productcatalog.dto.CharacteristicTypeDto;
import com.tallstech.sordman.domain.salesorder.document.SalesOrder;
import com.tallstech.sordman.domain.salesorder.dto.AddressTypeDto;
import com.tallstech.sordman.domain.salesorder.dto.AttachmentTypeDto;
import com.tallstech.sordman.domain.salesorder.dto.BillingInfoTypeDto;
import com.tallstech.sordman.domain.salesorder.dto.CartItemTypeDto;
import com.tallstech.sordman.domain.salesorder.dto.ContactPersonTypeDto;
import com.tallstech.sordman.domain.salesorder.dto.ContactPointTypeDto;
import com.tallstech.sordman.domain.salesorder.dto.ContactTypeDto;
import com.tallstech.sordman.domain.salesorder.dto.IdTypeDto;
import com.tallstech.sordman.domain.salesorder.dto.IdentificationTypeDto;
import com.tallstech.sordman.domain.salesorder.dto.IndividualTypeDto;
import com.tallstech.sordman.domain.salesorder.dto.OrganizationTypeDto;
import com.tallstech.sordman.domain.salesorder.dto.PriceInfoDto;
import com.tallstech.sordman.domain.salesorder.dto.RelatedPartyTypeDto;
import com.tallstech.sordman.domain.salesorder.dto.SalesOrderCreateDto;
import com.tallstech.sordman.domain.salesorder.dto.SalesOrderDto;
import com.tallstech.sordman.domain.salesorder.dto.TaxTypeDto;
import com.tallstech.sordman.model.PriceInfo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = "spring")
public interface SalesOrderMapper extends BaseMapper<SalesOrder, SalesOrderDto> {

    SalesOrder toDoc(SalesOrderCreateDto salesOrderCreateDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toDocForPatch(SalesOrderDto salesOrderDto, @MappingTarget SalesOrder salesOrder);

    CharacteristicType toDocType(CharacteristicTypeDto characteristicTypeDto);

    CharacteristicTypeDto toDtoType(CharacteristicType characteristicType);

    AddressType toDocType(AddressTypeDto addressTypeDto);

    AddressTypeDto toDtoType(AddressType addressType);

    AttachmentType toDocType(AttachmentTypeDto attachmentTypeDto);

    AttachmentTypeDto toDtoType(AttachmentType attachmentType);

    BillingInfoType toDocType(BillingInfoTypeDto billingInfoTypeDto);

    BillingInfoTypeDto toDtoType(BillingInfoType billingInfoType);

    ContactPersonType toDocType(ContactPersonTypeDto contactPersonTypeDto);

    ContactPersonTypeDto toDtoType(ContactPersonType contactPersonType);

    ContactPointType toDocType(ContactPointTypeDto contactPointTypeDto);

    ContactPointTypeDto toDtoType(ContactPointType contactPointType);

    ContactType toDocType(ContactTypeDto contactTypeDto);

    ContactTypeDto toDtoType(ContactType contactType);

    IdentificationType toDocType(IdentificationTypeDto identificationTypeDto);

    IdentificationTypeDto toDtoType(IdentificationType identificationType);

    IndividualType toDocType(IndividualTypeDto individualTypeDto);

    IndividualTypeDto toDtoType(IndividualType individualType);

    OrganizationType toDocType(OrganizationTypeDto organizationTypeDto);

    OrganizationTypeDto toDtoType(OrganizationType organizationType);

    RelatedPartyType toDocType(RelatedPartyTypeDto relatedPartyTypeDto);

    RelatedPartyTypeDto toDtoType(RelatedPartyType relatedPartyType);

    TaxType toDocType(TaxTypeDto taxTypeDto);

    TaxTypeDto toDtoType(TaxType taxType);

    IdType toDocType(IdTypeDto idTypeDto);

    IdTypeDto toDtoType(IdType idType);

    CartItemType toDocType(CartItemTypeDto cartItemTypeDto);

    CartItemTypeDto toDtoType(CartItemType cartItemType);

    PriceInfo toDocType(PriceInfoDto priceInfoDto);

    PriceInfoDto toDtoType(PriceInfo priceInfo);

}
