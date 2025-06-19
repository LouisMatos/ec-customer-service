package br.matosit.customer_service.adapters.in.rest.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.matosit.customer_service.adapters.in.rest.mappers.CustomerMapper;
import br.matosit.customer_service.adapters.in.rest.responses.CustomerResponse;
import br.matosit.customer_service.application.ports.in.FindCustomerUseCase;
import br.matosit.customer_service.domain.entities.Customer;

@RestController
@RequestMapping("/api/customers")
public class FindCustomerController {

  private static final Logger log = LoggerFactory.getLogger(FindCustomerController.class);
  private final FindCustomerUseCase findCustomerUseCase;

  public FindCustomerController(FindCustomerUseCase findCustomerUseCase) {
    this.findCustomerUseCase = findCustomerUseCase;
  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomerResponse> getCustomer(@PathVariable String id) {
    log.info("Buscando cliente: {}", id);
    Customer customer = findCustomerUseCase.find(id);
    log.info("Cliente encontrado: {}", customer.getId());
    return ResponseEntity.ok(CustomerMapper.toResponse(customer));
  }
}
