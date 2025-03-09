package br.matosit.customer_service.presentation.mappers;

import java.util.stream.Collectors;
import br.matosit.customer_service.domain.entities.Customer;
import br.matosit.customer_service.presentation.requests.CreateCustomerRequest;
import br.matosit.customer_service.presentation.requests.UpdateCustomerRequest;
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
}
