package br.matosit.customer_service.adapters.in.rest.controllers;

import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.matosit.customer_service.adapters.in.rest.mappers.CustomerMapper;
import br.matosit.customer_service.adapters.in.rest.requests.CreateCustomerRequest;
import br.matosit.customer_service.adapters.in.rest.responses.CustomerResponse;
import br.matosit.customer_service.application.ports.in.CreateCustomerUseCase;
import br.matosit.customer_service.domain.entities.Customer;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/customers")
public class CreateCustomerController {

  private static final Logger log = LoggerFactory.getLogger(CreateCustomerController.class);

  private final CreateCustomerUseCase createCustomerUseCase;

  public CreateCustomerController(CreateCustomerUseCase createCustomerUseCase) {
    this.createCustomerUseCase = createCustomerUseCase;
  }

  @PostMapping
  public ResponseEntity<CustomerResponse> createCustomer(
      @Valid @RequestBody CreateCustomerRequest request) {

    log.info("Iniciando criação de cliente: {}", request.name());

    Customer createdCustomer = createCustomerUseCase.create(CustomerMapper.toDomain(request));

    return ResponseEntity.created(URI.create("/api/customers/" + createdCustomer.getId()))
        .body(CustomerMapper.toResponse(createdCustomer));
  }
}
