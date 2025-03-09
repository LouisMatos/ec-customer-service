
package br.matosit.customer_service.application.usecases;

import java.util.Optional;
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

  Logger log = LoggerFactory.getLogger(UpdateCustomerUseCase.class);

  public UpdateCustomerUseCase(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Transactional
  public Customer execute(String id, Customer customer) {
    try {
      log.info("Validando se o cliente existe: {}", id);

      Optional<Customer> existingCustomer = customerRepository.findById(id);
      log.info("Cliente encontrado: {}", existingCustomer);

      if (existingCustomer.isEmpty()) {
        log.warn("Cliente não encontrado: {}", id);
        throw new CustomerNotFoundException(id);
      }

      log.info("Verificando se o email já está em uso: {}", customer.getEmail());
      Customer customerWithEmail = customerRepository.findByEmail(customer.getEmail());
      if (customerWithEmail != null && !customerWithEmail.getId().equals(id)) {
        log.warn("Email já está em uso por outro cliente: {}", customer.getEmail());
        throw new DuplicateKeyException("Email já está em uso por outro cliente");
      }

      log.info("Atualizando cliente: {}", id);

      Customer customerToUpdate = existingCustomer.get();
      customerToUpdate.setName(customer.getName());
      customerToUpdate.setEmail(customer.getEmail());
      customerToUpdate.setPhone(customer.getPhone());
      customerToUpdate.setAddresses(customer.getAddresses());

      log.info("TO STRING -> Cliente atualizado: {}", customerToUpdate.toString());

      Customer updatedCustomer = customerRepository.save(customerToUpdate);
      log.info("Cliente atualizado: {}", updatedCustomer.getId());

      return updatedCustomer;
    } catch (DuplicateKeyException e) {
      log.error("Erro ao atualizar cliente: Email duplicado {}", e.getMessage());
      throw e;
    } catch (Exception e) {
      log.error("Erro ao atualizar cliente: {}", e.getMessage());
      throw new RuntimeException("Erro ao atualizar cliente", e);
    }
  }
}
