package br.matosit.customer_service.application.usecases;

import org.springframework.stereotype.Service;

import br.matosit.customer_service.application.ports.CustomerRepository;
import br.matosit.customer_service.domain.entities.Customer;

@Service
public class CreateCustomerUseCase {
    private final CustomerRepository customerRepository;

    public CreateCustomerUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer execute(Customer customer) {
        // Validação de negócio
        Customer existingCustomer = customerRepository.findByEmail(customer.getEmail());
        if (existingCustomer != null) {
            throw new IllegalArgumentException("Cliente já existe com este email");
        }
        
        // Persistir e retornar
        return customerRepository.save(customer);
    }
} 