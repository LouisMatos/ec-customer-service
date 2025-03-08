package br.matosit.customer_service.application.ports;

import java.util.Optional;

import br.matosit.customer_service.domain.entities.Customer;

public interface CustomerRepository {
    Customer save(Customer customer);
    Optional<Customer> findById(String id);
    Customer findByEmail(String email);
} 