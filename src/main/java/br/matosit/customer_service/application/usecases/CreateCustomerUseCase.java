package br.matosit.customer_service.application.usecases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import br.matosit.customer_service.application.ports.CustomerRepository;
import br.matosit.customer_service.domain.entities.Customer;
import br.matosit.customer_service.domain.exceptions.CustomerAlreadyExistsException;

@Service
public class CreateCustomerUseCase {

  private final CustomerRepository customerRepository;

  Logger log = LoggerFactory.getLogger(CreateCustomerUseCase.class);

  public CreateCustomerUseCase(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public Customer execute(Customer customer) {
    // Validação de negócio
    log.info("Validando se o cliente já existe: {}", customer.getEmail());

    Customer existingCustomer = customerRepository.findByEmail(customer.getEmail());
    log.info("Cliente existente: {}", existingCustomer);

    if (existingCustomer != null) {
      log.warn("Cliente já existe: {}", customer.getEmail());
      throw new CustomerAlreadyExistsException(customer.getEmail());
    }

    // Persistir e retornar
    log.info("Criando cliente: {}", customer.getEmail());
    return customerRepository.save(customer);
  }
}
