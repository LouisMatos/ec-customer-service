package br.matosit.customer_service.adapters.out.persistence;

import java.util.List;
import java.util.stream.Collectors;
import br.matosit.customer_service.domain.entities.Address;
import br.matosit.customer_service.domain.entities.Customer;

public class CustomerPersistenceMapper {

  public static CustomerDocument toDocument(Customer customer) {
    List<AddressDocument> addresses = customer.getAddresses().stream()
        .map(CustomerPersistenceMapper::toDocument)
        .collect(Collectors.toList());
    return new CustomerDocument(customer.getId(), customer.getName(), customer.getEmail(),
        customer.getPhone(), addresses);
  }

  public static Customer toDomain(CustomerDocument doc) {
    List<Address> addresses = doc.getAddresses().stream()
        .map(CustomerPersistenceMapper::toDomain)
        .collect(Collectors.toList());
    Customer customer = new Customer(doc.getName(), doc.getEmail(), doc.getPhone(), addresses);
    customer.setId(doc.getId());
    return customer;
  }

  public static AddressDocument toDocument(Address address) {
    return new AddressDocument(address.getStreet(), address.getNumber(), address.getComplement(),
        address.getNeighborhood(), address.getCity(), address.getState(), address.getZipCode());
  }

  public static Address toDomain(AddressDocument doc) {
    return new Address(doc.getStreet(), doc.getNumber(), doc.getComplement(), doc.getNeighborhood(),
        doc.getCity(), doc.getState(), doc.getZipCode());
  }
}

