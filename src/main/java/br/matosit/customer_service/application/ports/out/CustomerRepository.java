package br.matosit.customer_service.application.ports.out;

import java.util.Optional;
import br.matosit.customer_service.domain.entities.Customer;

public interface CustomerRepository {

  Customer save(Customer customer);

  Optional<Customer> findById(String id);

  void deleteById(String id);

  Optional<Customer> findByEmail(String email);
}
