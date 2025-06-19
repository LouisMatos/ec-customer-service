package br.matosit.customer_service.application.ports.in;

import br.matosit.customer_service.domain.entities.Customer;

public interface CreateCustomerUseCase {
  Customer create(Customer customer);
}
