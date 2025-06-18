package br.matosit.customer_service.application.usecases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import br.matosit.customer_service.application.ports.in.UpdateCustomerUseCase;
import br.matosit.customer_service.application.ports.out.CustomerRepository;
import br.matosit.customer_service.domain.entities.Customer;
import br.matosit.customer_service.domain.exceptions.CustomerNotFoundException;

@Service
public class UpdateCustomerUseCaseImpl implements UpdateCustomerUseCase {

  private static final Logger log = LoggerFactory.getLogger(UpdateCustomerUseCaseImpl.class);
  private static final String EMAIL_IN_USE_MSG = "Email já está em uso por outro cliente";

  private final CustomerRepository customerRepository;

  public UpdateCustomerUseCaseImpl(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public Customer update(String id, Customer customer) {
    log.info("Validando se o cliente existe: {}", id);
    Customer existingCustomer = customerRepository.findById(id).orElseThrow(() -> {
      log.warn("Cliente não encontrado: {}", id);
      return new CustomerNotFoundException(id);
    });

    validateEmailUniqueness(id, customer.getEmail());

    Customer updatedCustomer =
        customerRepository.save(prepareCustomerForUpdate(existingCustomer, customer));

    log.info("Cliente atualizado: {}", updatedCustomer.getId());
    return updatedCustomer;
  }

  private void validateEmailUniqueness(String id, String email) {
    customerRepository.findByEmail(email).filter(c -> !c.getId().equals(id)).ifPresent(c -> {
      log.warn("Email já está em uso por outro cliente: {}", email);
      throw new DuplicateKeyException(EMAIL_IN_USE_MSG);
    });
  }

  private Customer prepareCustomerForUpdate(Customer existing, Customer updates) {
    existing.setName(updates.getName());
    existing.setEmail(updates.getEmail());
    existing.setPhone(updates.getPhone());
    existing.setAddresses(updates.getAddresses());
    return existing;
  }
}
