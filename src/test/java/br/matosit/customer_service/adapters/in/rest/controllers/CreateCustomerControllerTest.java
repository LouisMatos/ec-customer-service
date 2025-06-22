package br.matosit.customer_service.adapters.in.rest.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import br.matosit.customer_service.adapters.in.rest.requests.CreateCustomerRequest;
import br.matosit.customer_service.adapters.in.rest.responses.CustomerResponse;
import br.matosit.customer_service.application.ports.in.CreateCustomerUseCase;
import br.matosit.customer_service.application.ports.out.CustomerRepository;
import br.matosit.customer_service.domain.entities.Customer;

class CreateCustomerControllerTest {

  @Mock
  private CreateCustomerUseCase createCustomerUseCase;

  @Mock
  private CustomerRepository customerRepository;

  @InjectMocks
  private CreateCustomerController controller;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createCustomer_ShouldReturnCreated_WhenValidRequest() {

    CreateCustomerRequest request =
        new CreateCustomerRequest("1", "John Doe", "john@example.com", Collections.emptyList());

    CustomerResponse expectedCustomerResponse =
        new CustomerResponse(null, "1", "John Doe", "john@example.com", Collections.emptyList());

    Customer customer = new Customer("1", "John Doe", "john@example.com", Collections.emptyList());
    when(createCustomerUseCase.create(any(Customer.class))).thenReturn(customer);

    ResponseEntity<CustomerResponse> response = controller.createCustomer(request);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(expectedCustomerResponse, response.getBody());
    verify(createCustomerUseCase).create(any(Customer.class));
  }

}
