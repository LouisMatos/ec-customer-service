package br.matosit.customer_service.adapters.out.persistence;

import java.util.Optional;
import org.springframework.stereotype.Component;
import br.matosit.customer_service.application.ports.out.CustomerRepository;
import br.matosit.customer_service.domain.entities.Customer;

@Component
public class CustomerRepositoryImpl implements CustomerRepository {

  private final MongoCustomerRepository mongoCustomerRepository;

  public CustomerRepositoryImpl(MongoCustomerRepository mongoCustomerRepository) {
    this.mongoCustomerRepository = mongoCustomerRepository;
  }

  @Override
  public Customer save(Customer customer) {
    CustomerDocument doc = CustomerPersistenceMapper.toDocument(customer);
    CustomerDocument saved = mongoCustomerRepository.save(doc);
    return CustomerPersistenceMapper.toDomain(saved);
  }

  @Override
  public Optional<Customer> findById(String id) {
    return mongoCustomerRepository.findById(id).map(CustomerPersistenceMapper::toDomain);
  }

  @Override
  public void deleteById(String id) {
    mongoCustomerRepository.deleteById(id);
  }

  @Override
  public Optional<Customer> findByEmail(String email) {
    return mongoCustomerRepository.findByEmail(email).map(CustomerPersistenceMapper::toDomain);
  }
}

