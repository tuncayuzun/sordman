package com.tallstech.sordman.domain.productcatalog.mapper;

import java.util.ArrayList;
import java.util.List;

import com.tallstech.sordman.domain.base.BaseMapper;
import com.tallstech.sordman.domain.base.type.CharacteristicType;
import com.tallstech.sordman.domain.base.type.DescriptionType;
import com.tallstech.sordman.domain.base.type.IdType;
import com.tallstech.sordman.domain.base.type.OfferingInfoType;
import com.tallstech.sordman.domain.base.type.SalesType;
import com.tallstech.sordman.domain.base.type.TaxType;
import com.tallstech.sordman.domain.productcatalog.document.ProductCatalog;
import com.tallstech.sordman.domain.productcatalog.dto.CharacteristicTypeDto;
import com.tallstech.sordman.domain.productcatalog.dto.DescriptionTypeDto;
import com.tallstech.sordman.domain.productcatalog.dto.IdTypeDto;
import com.tallstech.sordman.domain.productcatalog.dto.OfferingInfoTypeDto;
import com.tallstech.sordman.domain.productcatalog.dto.ProductCatalogCreateDto;
import com.tallstech.sordman.domain.productcatalog.dto.ProductCatalogDto;
import com.tallstech.sordman.domain.productcatalog.dto.SalesTypeDto;
import com.tallstech.sordman.domain.productcatalog.dto.TaxTypeDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ProductCatalogMapper extends BaseMapper<ProductCatalog, ProductCatalogDto> {
    ProductCatalog toDoc(ProductCatalogCreateDto productCatalogCreateDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toDocForPatch(ProductCatalogDto productCatalogDto, @MappingTarget ProductCatalog productCatalog);
    default void toDocForPatch(List<IdTypeDto> idTypeDtoList, @MappingTarget List<IdType> idTypeList) {
        List<IdTypeDto> tempList = new ArrayList<>();
        tempList.addAll(idTypeDtoList);
        for (IdTypeDto idTypeDto : idTypeDtoList) {
            for (IdType idType : idTypeList) {
                if (idTypeDto.name().equalsIgnoreCase(idType.getName())) {
                    idType.setValue(idTypeDto.value());
                    tempList.remove(idTypeDto);
                }
            }
        }
        if (!tempList.isEmpty()) {
            for (IdTypeDto idTypeDto : tempList) {
                idTypeList.add(toDocType(idTypeDto));
            }
        }
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toDocForPatch(DescriptionTypeDto descriptionTypeDto, @MappingTarget DescriptionType descriptionType);
    default void toDocForPatchDescription(List<CharacteristicTypeDto> characteristicTypeDtoList, @MappingTarget List<CharacteristicType> characteristicTypeList) {
        List<CharacteristicTypeDto> tempList = new ArrayList<>();
        tempList.addAll(characteristicTypeDtoList);
        for (CharacteristicTypeDto characteristicTypeDto : characteristicTypeDtoList) {
            for (CharacteristicType characteristicType : characteristicTypeList) {
                if (characteristicTypeDto.name().equalsIgnoreCase(characteristicType.getName())) {
                    characteristicType.setValue(characteristicTypeDto.value());
                    characteristicType.setType(characteristicTypeDto.type());
                    characteristicType.setDescription(characteristicType.getDescription());
                    tempList.remove(characteristicTypeDto);
                }
            }
        }

        if (!tempList.isEmpty()) {
            for (CharacteristicTypeDto characteristicTypeDto : tempList) {
                characteristicTypeList.add(toDocType(characteristicTypeDto));
            }
        }
    }

    IdType toDocType(IdTypeDto idTypeDto);

    IdTypeDto toDtoType(IdType idType);

    DescriptionType toDocType(DescriptionTypeDto descriptionTypeDto);

    DescriptionTypeDto toDtoType(DescriptionType descriptionType);

    CharacteristicType toDocType(CharacteristicTypeDto characteristicTypeDto);

    CharacteristicTypeDto toDtoType(CharacteristicType characteristicType);

    OfferingInfoType toDocType(OfferingInfoTypeDto offeringInfoTypeDto);

    OfferingInfoTypeDto toDtoType(OfferingInfoType offeringInfoType);

    default void toDocForPatchOfferingInfo(List<OfferingInfoTypeDto> offeringInfoTypeDtoList, @MappingTarget List<OfferingInfoType> offeringInfoTypeList) {
        List<OfferingInfoTypeDto> tempList = new ArrayList<>();
        tempList.addAll(offeringInfoTypeDtoList);
        for (OfferingInfoTypeDto offeringInfoTypeDto : offeringInfoTypeDtoList) {
            for (OfferingInfoType offeringInfoType : offeringInfoTypeList) {
                if (offeringInfoTypeDto.catalogId().equalsIgnoreCase(offeringInfoType.getCatalogId())) {
                    offeringInfoType.setName(offeringInfoTypeDto.name());
                    offeringInfoType.setCategory(offeringInfoTypeDto.category());
                    offeringInfoType.setSubCategory(offeringInfoType.getSubCategory());
                    offeringInfoType.setCode(offeringInfoType.getCode());
                    tempList.remove(offeringInfoTypeDto);
                }
            }
        }

        if (!tempList.isEmpty()) {
            for (OfferingInfoTypeDto offeringInfoTypeDto : tempList) {
                offeringInfoTypeList.add(toDocType(offeringInfoTypeDto));
            }
        }
    }

    TaxType toDocType(TaxTypeDto taxTypeDto);

    TaxTypeDto toDtoType(TaxType taxType);

    SalesType toDocType(SalesTypeDto salesTypeDto);

    SalesTypeDto toDtoType(SalesType salesType);

}
