package br.matosit.customer_service.application.usecases;

import java.util.Optional;
import org.springframework.stereotype.Service;
import br.matosit.customer_service.application.ports.CustomerRepository;
import br.matosit.customer_service.domain.entities.Customer;
import br.matosit.customer_service.domain.exceptions.CustomerNotFoundException;

@Service
public class FindCustomerUseCase {

  private final CustomerRepository customerRepository;

  public FindCustomerUseCase(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public Customer execute(String id) {
    // Validação de negócio
    Optional<Customer> existingCustomer = customerRepository.findById(id);

    if (existingCustomer.isEmpty()) {
      throw new CustomerNotFoundException(id);
    }

    return existingCustomer.get();
  }
}
