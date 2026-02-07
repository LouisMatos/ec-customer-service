package br.matosit.customer_service.adapters.in.rest.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.matosit.customer_service.adapters.in.rest.mappers.CustomerMapper;
import br.matosit.customer_service.adapters.in.rest.dto.UpdateCustomerRequest;
import br.matosit.customer_service.adapters.in.rest.dto.CustomerResponse;
import br.matosit.customer_service.application.ports.in.UpdateCustomerUseCase;
import br.matosit.customer_service.domain.entities.Customer;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers")
public class UpdateCustomerController {

  private static final Logger log = LoggerFactory.getLogger(UpdateCustomerController.class);

  private final UpdateCustomerUseCase updateCustomerUseCase;

  public UpdateCustomerController(UpdateCustomerUseCase updateCustomerUseCase) {
    this.updateCustomerUseCase = updateCustomerUseCase;
  }

  @PutMapping("/{id}")
  public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable String id,
      @Valid @RequestBody UpdateCustomerRequest request) {

    log.info("Iniciando atualização do cliente: {}", id);

    Customer updatedCustomer = updateCustomerUseCase.update(id, CustomerMapper.toDomain(request));

    return ResponseEntity.ok(CustomerMapper.toResponse(updatedCustomer));
  }
}
