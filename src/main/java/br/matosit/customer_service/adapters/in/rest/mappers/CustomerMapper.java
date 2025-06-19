package br.matosit.customer_service.adapters.in.rest.mappers;

import java.util.stream.Collectors;
import br.matosit.customer_service.adapters.in.rest.requests.CreateCustomerRequest;
import br.matosit.customer_service.adapters.in.rest.requests.UpdateCustomerRequest;
import br.matosit.customer_service.adapters.in.rest.responses.CustomerResponse;
import br.matosit.customer_service.domain.entities.Customer;
import jakarta.validation.Valid;

public class CustomerMapper {


  public static Customer toDomain(CreateCustomerRequest request) {
    var addresses =
        request.addresses().stream().map(AddressMapper::toDomain).collect(Collectors.toList());

    return new Customer(request.name(), request.email(), request.phone(), addresses);
  }

  public static Customer toDomain(@Valid UpdateCustomerRequest request) {
    var addresses =
        request.addresses().stream().map(AddressMapper::toDomain).collect(Collectors.toList());

    return new Customer(request.name(), request.email(), request.phone(), addresses);
  }

  public static CustomerResponse toResponse(Customer createdCustomer) {
    var addressResponses = createdCustomer.getAddresses().stream().map(AddressMapper::toResponse)
        .collect(Collectors.toList());

    return new CustomerResponse(createdCustomer.getId(), createdCustomer.getName(),
        createdCustomer.getEmail(), createdCustomer.getPhone(), addressResponses);
  }

}
