package br.matosit.customer_service.presentation.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.matosit.customer_service.application.usecases.DeleteCustomerUseCase;
import br.matosit.customer_service.domain.entities.Customer;

@RestController
@RequestMapping("/api/customers")
public class DeleteCustomerController {

  private final DeleteCustomerUseCase deleteCustomerUseCase;

  Logger log = LoggerFactory.getLogger(DeleteCustomerController.class);

  public DeleteCustomerController(DeleteCustomerUseCase deleteCustomerUseCase) {
    this.deleteCustomerUseCase = deleteCustomerUseCase;
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Customer> getCustomer(@PathVariable String id) {
    log.info("Iniciando deleção do cliente: {}", id);
    deleteCustomerUseCase.execute(id);
    log.info("Cliente deletado com sucesso: {}", id);
    return ResponseEntity.noContent().build();
  }
}
