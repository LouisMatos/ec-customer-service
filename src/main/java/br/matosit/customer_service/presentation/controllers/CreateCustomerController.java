package br.matosit.customer_service.presentation.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CreateCustomerController {

  private final CreateCustomerUseCase createCustomerUseCase;

  Logger log = LoggerFactory.getLogger(CreateCustomerController.class);

  public CreateCustomerController(CreateCustomerUseCase createCustomerUseCase) {
    this.createCustomerUseCase = createCustomerUseCase;
  }

  @PostMapping
  public ResponseEntity<Customer> createCustomer(
      @Valid @RequestBody CreateCustomerRequest request) {
    log.info("Iniciando criação de cliente: {}", request);
    Customer customer = CustomerMapper.toDomain(request);
    Customer createdCustomer = createCustomerUseCase.execute(customer);
    log.info("Cliente criado com sucesso: {}", createdCustomer.getId());
    return ResponseEntity.ok(createdCustomer);
  }

}
