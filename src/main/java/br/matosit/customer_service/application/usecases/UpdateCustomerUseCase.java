package br.matosit.customer_service.application.usecases;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.matosit.customer_service.application.ports.CustomerRepository;
import br.matosit.customer_service.domain.entities.Customer;
import br.matosit.customer_service.domain.exceptions.CustomerNotFoundException;

@Service
public class UpdateCustomerUseCase {

  private final CustomerRepository customerRepository;
  private final Logger log = LoggerFactory.getLogger(UpdateCustomerUseCase.class);

  public UpdateCustomerUseCase(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Transactional
  public Customer execute(String id, Customer customer) {
    log.info("Validando se o cliente existe: {}", id);
    Customer existingCustomer = customerRepository.findById(id)
        .orElseThrow(() -> {
          log.warn("Cliente não encontrado: {}", id);
          return new CustomerNotFoundException(id);
        });

    log.info("Verificando se o email já está em uso: {}", customer.getEmail());
    Customer customerWithEmail = customerRepository.findByEmail(customer.getEmail());
    if (customerWithEmail != null && !customerWithEmail.getId().equals(id)) {
      log.warn("Email já está em uso por outro cliente: {}", customer.getEmail());
      throw new DuplicateKeyException("Email já está em uso por outro cliente");
    }

    log.info("Atualizando cliente: {}", id);
    existingCustomer.setName(customer.getName());
    existingCustomer.setEmail(customer.getEmail());
    existingCustomer.setPhone(customer.getPhone());
    existingCustomer.setAddresses(customer.getAddresses());

    Customer updatedCustomer = customerRepository.save(existingCustomer);
    log.info("Cliente atualizado: {}", updatedCustomer.getId());

    return updatedCustomer;
  }
}
