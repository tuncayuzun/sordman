package com.tallstech.sordman.domain.salesorder.util;

import com.tallstech.sordman.domain.base.type.IdType;
import com.tallstech.sordman.model.Address;
import com.tallstech.sordman.model.Customer;
import com.tallstech.sordman.model.Identification;
import com.tallstech.sordman.domain.salesorder.document.SalesOrder;

import java.time.LocalDateTime;
import java.util.List;

public final class SalesOrderUtils {

    private SalesOrderUtils() {
    }

    public static Object getValueFromSalesOrderRelations(SalesOrder salesOrder, String requestedRelationName) {
        return salesOrder.getRelations().stream()
                .filter(item -> requestedRelationName.equalsIgnoreCase(item.getName()))
                .map(IdType::getValue)
                .findFirst()
                .orElse(null);
    }

    public static Customer generateTempCustomer(){
       Customer customer = new Customer();
       customer.setId(Long.valueOf(1111));
       customer.setFirstName("Tuncay");
       customer.setLastName("Uzun");
       customer.setEmail("tuncay@talls.tech");
       customer.setType("individual");

       Identification identification = new Identification();
       identification.setType("tckn");
       identification.setValue("12345678910");
       customer.setIdentification(identification);
       customer.setDescription("Platinum Customer");
       customer.setRegistrationDate(LocalDateTime.now().minusMonths(1));
       customer.setLastLoginDate(LocalDateTime.now().minusHours(1));

       Address address = new Address();
       address.setContactName("Tuncay Uzun");
       address.setCountry("Türkiye");
       address.setCity("İstanbul");
       address.setDistrict("Sisli");
       address.setDetail("Sisli");
       address.setPhone("905559998877");
       address.setCustomerId(Long.valueOf(1111));
       address.setAccountId(Long.valueOf(2222));
       address.setPostalCode("34444");
       customer.setAddresses(List.of(address));
       return customer;
    }
}
