package br.matosit.customer_service.application.usecases;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import br.matosit.customer_service.application.ports.in.DeleteCustomerUseCase;
import br.matosit.customer_service.application.ports.out.CustomerRepository;
import br.matosit.customer_service.domain.entities.Customer;
import br.matosit.customer_service.domain.exceptions.CustomerNotFoundException;

@Service
public class DeleteCustomerUseCaseImpl implements DeleteCustomerUseCase {

  private final CustomerRepository customerRepository;

  Logger log = LoggerFactory.getLogger(DeleteCustomerUseCaseImpl.class);

  public DeleteCustomerUseCaseImpl(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public void delete(String id) {
    // Validação de negócio
    log.info("Validando existência do cliente: {}", id);

    Optional<Customer> existingCustomer = customerRepository.findById(id);
    log.info("Cliente existente: {}", existingCustomer);

    if (existingCustomer.isEmpty()) {
      log.warn("Cliente não encontrado: {}", id);
      throw new CustomerNotFoundException(id);
    }

    // Persistir e retornar
    log.info("Deletando cliente: {}", id);
    customerRepository.deleteById(id);
  }
}
