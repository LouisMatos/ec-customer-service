package br.matosit.customer_service.application.usecases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import br.matosit.customer_service.application.ports.in.CreateCustomerUseCase;
import br.matosit.customer_service.application.ports.out.CustomerRepository;
import br.matosit.customer_service.domain.entities.Customer;
import br.matosit.customer_service.domain.exceptions.CustomerAlreadyExistsException;

@Service
public class CreateCustomerUseCaseImpl implements CreateCustomerUseCase {

  private final CustomerRepository customerRepository;

  Logger log = LoggerFactory.getLogger(CreateCustomerUseCaseImpl.class);

  public CreateCustomerUseCaseImpl(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public Customer create(Customer customer) {
    log.info("Validando se o cliente já existe: {}", customer.getEmail());

    customerRepository.findByEmail(customer.getEmail()).ifPresent(existing -> {
      log.warn("Cliente já existe: {}", customer.getEmail());
      throw new CustomerAlreadyExistsException(customer.getEmail());
    });

    log.info("Criando cliente: {}", customer.getEmail());
    return customerRepository.save(customer);
  }
}
