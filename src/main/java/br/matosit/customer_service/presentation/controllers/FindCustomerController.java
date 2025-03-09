package br.matosit.customer_service.presentation.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.matosit.customer_service.application.usecases.FindCustomerUseCase;
import br.matosit.customer_service.domain.entities.Customer;

@RestController
@RequestMapping("/api/customers")
public class FindCustomerController {

  private final FindCustomerUseCase findCustomerUseCase;

  Logger log = LoggerFactory.getLogger(FindCustomerController.class);

  public FindCustomerController(FindCustomerUseCase findCustomerUseCase) {
    this.findCustomerUseCase = findCustomerUseCase;
  }

  @GetMapping("/{id}")
  public ResponseEntity<Customer> getCustomer(@PathVariable String id) {
    log.info("Buscando cliente: {}", id);
    Customer customer = findCustomerUseCase.execute(id);
    log.info("Cliente encontrado: {}", customer.getId());
    return ResponseEntity.ok(customer);
  }
}
