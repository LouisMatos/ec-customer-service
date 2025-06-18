package br.matosit.customer_service.adapters.in.rest.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.matosit.customer_service.application.ports.in.DeleteCustomerUseCase;

@RestController
@RequestMapping("/api/customers")
public class DeleteCustomerController {

  private static final Logger log = LoggerFactory.getLogger(DeleteCustomerController.class);

  private final DeleteCustomerUseCase deleteCustomerUseCase;

  public DeleteCustomerController(DeleteCustomerUseCase deleteCustomerUseCase) {
    this.deleteCustomerUseCase = deleteCustomerUseCase;
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {
    log.info("Iniciando deleção do cliente: {}", id);
    deleteCustomerUseCase.delete(id);
    log.info("Cliente deletado com sucesso: {}", id);
    return ResponseEntity.noContent().build();
  }
}
