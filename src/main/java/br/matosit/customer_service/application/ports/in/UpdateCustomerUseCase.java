package br.matosit.customer_service.application.ports.in;

import br.matosit.customer_service.domain.entities.Customer;

public interface UpdateCustomerUseCase {

  Customer update(String id, Customer customer);

}
