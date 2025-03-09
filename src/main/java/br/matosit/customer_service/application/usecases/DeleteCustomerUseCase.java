package br.matosit.customer_service.application.usecases;

import java.util.Optional;
import org.springframework.stereotype.Service;
import br.matosit.customer_service.application.ports.CustomerRepository;
import br.matosit.customer_service.domain.entities.Customer;
import br.matosit.customer_service.domain.exceptions.CustomerNotFoundException;

@Service
public class DeleteCustomerUseCase {

  private final CustomerRepository customerRepository;

  public DeleteCustomerUseCase(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public void execute(String id) {
    // Validação de negócio
    Optional<Customer> existingCustomer = customerRepository.findById(id);

    if (existingCustomer.isEmpty()) {
      throw new CustomerNotFoundException(id);
    }

    customerRepository.deleteById(id);
  }
}
