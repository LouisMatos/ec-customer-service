package br.matosit.customer_service.presentation.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.matosit.customer_service.application.usecases.CreateCustomerUseCase;
import br.matosit.customer_service.domain.entities.Customer;
import br.matosit.customer_service.presentation.mappers.CustomerMapper;
import br.matosit.customer_service.presentation.requests.CreateCustomerRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CreateCustomerUseCase createCustomerUseCase;

    public CustomerController(CreateCustomerUseCase createCustomerUseCase) {
        this.createCustomerUseCase = createCustomerUseCase;
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        Customer customer = CustomerMapper.toDomain(request);
        Customer createdCustomer = createCustomerUseCase.execute(customer);
        return ResponseEntity.ok(createdCustomer);
    }
} 