package br.matosit.customer_service.infrastructure.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.matosit.customer_service.application.ports.CustomerRepository;
import br.matosit.customer_service.domain.entities.Customer;

@Repository
public interface MongoCustomerRepository extends MongoRepository<Customer, String>, CustomerRepository {
    Customer findByEmail(String email);
} 