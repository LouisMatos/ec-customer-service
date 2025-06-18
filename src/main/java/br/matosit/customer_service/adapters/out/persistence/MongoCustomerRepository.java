package br.matosit.customer_service.adapters.out.persistence;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import br.matosit.customer_service.application.ports.out.CustomerRepository;
import br.matosit.customer_service.domain.entities.Customer;

@Repository
public interface MongoCustomerRepository
    extends MongoRepository<Customer, String>, CustomerRepository {
  Optional<Customer> findByEmail(String email);
}
