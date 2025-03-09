package br.matosit.customer_service.presentation.controllers;

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

  public DeleteCustomerController(DeleteCustomerUseCase deleteCustomerUseCase) {
    this.deleteCustomerUseCase = deleteCustomerUseCase;
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Customer> getCustomer(@PathVariable String id) {
    deleteCustomerUseCase.execute(id);
    return ResponseEntity.noContent().build();
  }
}
