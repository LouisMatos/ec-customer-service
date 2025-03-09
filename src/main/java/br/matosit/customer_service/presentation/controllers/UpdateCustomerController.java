package br.matosit.customer_service.presentation.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.matosit.customer_service.application.usecases.UpdateCustomerUseCase;
import br.matosit.customer_service.domain.entities.Customer;
import br.matosit.customer_service.presentation.mappers.CustomerMapper;
import br.matosit.customer_service.presentation.requests.UpdateCustomerRequest;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/customers")
public class UpdateCustomerController {

  private final UpdateCustomerUseCase updateCustomerUseCase;

  Logger log = LoggerFactory.getLogger(UpdateCustomerController.class);

  public UpdateCustomerController(UpdateCustomerUseCase updateCustomerUseCase) {
    this.updateCustomerUseCase = updateCustomerUseCase;
  }


  @PutMapping("/{id}")
  public ResponseEntity<Customer> updateCustomer(@PathVariable String id,
      @Valid @RequestBody UpdateCustomerRequest request) {
    log.info("Iniciando atualização de cliente: {}", id);
    Customer customer = CustomerMapper.toDomain(request);
    Customer updatedCustomer = updateCustomerUseCase.execute(id, customer);
    log.info("Cliente atualizado com sucesso: {}", updatedCustomer.getId());
    return ResponseEntity.ok(updatedCustomer);
  }



}
