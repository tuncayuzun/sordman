package com.tallstech.sordman.util;


import static com.tallstech.sordman.constant.SordmanConstants.TYPE_CONTACT;
import static com.tallstech.sordman.constant.SordmanConstants.TYPE_CONTACT_EMAIL;
import static com.tallstech.sordman.constant.SordmanConstants.TYPE_CONTACT_PHONE;
import static com.tallstech.sordman.constant.SordmanConstants.TYPE_IDENTIFICATION;
import static com.tallstech.sordman.constant.SordmanConstants.TYPE_ORGANISATION;

import java.util.Arrays;

import com.tallstech.sordman.domain.base.type.AddressType;
import com.tallstech.sordman.domain.base.type.BillingInfoType;
import com.tallstech.sordman.domain.base.type.CharacteristicType;
import com.tallstech.sordman.domain.base.type.ContactPersonType;
import com.tallstech.sordman.domain.base.type.ContactPointType;
import com.tallstech.sordman.domain.base.type.ContactType;
import com.tallstech.sordman.domain.base.type.IdType;
import com.tallstech.sordman.domain.base.type.IdentificationType;
import com.tallstech.sordman.domain.base.type.IndividualType;
import com.tallstech.sordman.domain.base.type.OrganizationType;
import com.tallstech.sordman.model.Customer;
import org.apache.commons.lang3.ObjectUtils;


public final class Generator {

    private Generator() {
    }

    public static IdType generateIdType(String name, Object value) {
        var idType = new IdType();
        idType.setName(name);
        idType.setValue(value);
        return idType;
    }

    public static CharacteristicType generateCharacteristic(String name, Object value, String... withOrderTypeAndDescription) {
        var characteristicType = new CharacteristicType();
        characteristicType.setName(name);
        characteristicType.setValue(value);
        if (withOrderTypeAndDescription != null || withOrderTypeAndDescription.length > 0) {
            characteristicType.setType(withOrderTypeAndDescription[0]);
            if (withOrderTypeAndDescription.length > 1) {
                characteristicType.setDescription(withOrderTypeAndDescription[1]);
            }
        }
        return characteristicType;
    }

    public static ContactPointType generateContactPoint(CharacteristicType... characteristicTypes) {
        var contactPointType = new ContactPointType();
        contactPointType.setCharacteristics(Arrays.asList(characteristicTypes));
        return contactPointType;
    }

    public static BillingInfoType generateBillingInfo(IdentificationType identificationType, AddressType addressType) {
        var billingInfoType = new BillingInfoType();
        billingInfoType.setIdentification(identificationType);
        billingInfoType.setAddress(addressType);
        return billingInfoType;
    }

    public static IndividualType generateIndividual(Customer customer) {
        var individualType = new IndividualType();
        individualType.setId(customer.getId());
        individualType.setFirstName(customer.getFirstName());
        individualType.setFamilyName(customer.getLastName());

        var identificationType = new IdentificationType();
        identificationType.setIdentification(generateCharacteristic(TYPE_IDENTIFICATION, customer.getIdentification().getValue(), customer.getIdentification().getType()));
        individualType.setIdentification(identificationType);

        //TODO : will be refactored after customer object updated
        var contactType = new ContactType();
        contactType.setEmail(customer.getEmail());
        var address = customer.getAddresses().stream()
                .findFirst()
                .orElse(null);

        var addressType = new AddressType();
        if (ObjectUtils.isNotEmpty(address)) {
            contactType.setPhone(address.getPhone());
            addressType.setType(TYPE_CONTACT);
            addressType.setCity(addressType.getCity());
            addressType.setCountry(address.getCountry());
            addressType.setPostCode(address.getPostalCode());
            addressType.setCounty(address.getDistrict());
        }
        individualType.setBillingInfo(generateBillingInfo(identificationType, addressType));
        return individualType;
    }

    public static OrganizationType generateOrganization(Customer customer) {
        var organizationType = new OrganizationType();
        organizationType.setId(customer.getId());
        organizationType.setName(customer.getDescription());
        organizationType.setDescription(customer.getDescription());
        organizationType.setType(TYPE_ORGANISATION);

        var contactType = new ContactType();
        contactType.setEmail(customer.getEmail());
        var address = customer.getAddresses().stream()
                .findFirst()
                .orElse(null);

        var addressType = new AddressType();
        if (ObjectUtils.isNotEmpty(address)) {
            contactType.setPhone(address.getPhone());

            addressType.setType(TYPE_CONTACT);
            addressType.setCity(addressType.getCity());
            addressType.setCountry(address.getCountry());
            addressType.setPostCode(address.getPostalCode());
            addressType.setCounty(address.getDistrict());
        }

        organizationType.setContact(contactType);

        var contactPersonType = new ContactPersonType();
        contactPersonType.setFirstName(customer.getFirstName());
        contactPersonType.setFamilyName(customer.getLastName());
        contactPersonType.setContactPoint(generateContactPoint(
                generateCharacteristic(TYPE_CONTACT_EMAIL, customer.getEmail()),
                generateCharacteristic(TYPE_CONTACT_PHONE, address.getPhone())
        ));
        organizationType.setContactPerson(contactPersonType);

        var identificationType = new IdentificationType();
        identificationType.setIdentification(generateCharacteristic(TYPE_IDENTIFICATION, customer.getIdentification().getValue(), customer.getIdentification().getType()));

        organizationType.setBillingInfo(generateBillingInfo(identificationType, addressType));

        return organizationType;
    }
}
