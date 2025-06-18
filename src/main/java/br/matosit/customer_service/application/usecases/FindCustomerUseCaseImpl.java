package br.matosit.customer_service.application.usecases;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import br.matosit.customer_service.application.ports.in.FindCustomerUseCase;
import br.matosit.customer_service.application.ports.out.CustomerRepository;
import br.matosit.customer_service.domain.entities.Customer;
import br.matosit.customer_service.domain.exceptions.CustomerNotFoundException;

@Service
public class FindCustomerUseCaseImpl implements FindCustomerUseCase {

  private final CustomerRepository customerRepository;

  Logger log = LoggerFactory.getLogger(FindCustomerUseCaseImpl.class);

  public FindCustomerUseCaseImpl(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public Customer find(String id) {
    // Validação de negócio
    log.info("Buscando cliente: {}", id);

    Optional<Customer> existingCustomer = customerRepository.findById(id);
    log.info("Cliente encontrado: {}", existingCustomer);

    if (existingCustomer.isEmpty()) {
      log.warn("Cliente não encontrado: {}", id);
      throw new CustomerNotFoundException(id);
    }

    // Retornar
    log.info("Cliente encontrado: {}", existingCustomer.get().toString());
    return existingCustomer.get();
  }
}
