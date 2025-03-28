package br.matosit.customer_service.presentation.controllers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import br.matosit.customer_service.application.ports.CustomerRepository;
import br.matosit.customer_service.application.usecases.DeleteCustomerUseCase;
import br.matosit.customer_service.domain.exceptions.CustomerNotFoundException;

@WebMvcTest(DeleteCustomerController.class)
@ExtendWith(MockitoExtension.class)
public class DeleteCustomerControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private DeleteCustomerUseCase deleteCustomerUseCase;

  @MockBean
  private CustomerRepository customerRepository;
  
  @InjectMocks
  private DeleteCustomerController deleteCustomerController;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testDeleteCustomer_Success() throws Exception {
    doNothing().when(deleteCustomerUseCase).execute("123");

    mockMvc.perform(delete("/api/customers/123")).andExpect(status().isNoContent());

    verify(deleteCustomerUseCase, times(1)).execute("123");
  }

  @Test
  public void testDeleteCustomer_NotFound() throws Exception {
    doThrow(new CustomerNotFoundException("12333")).when(deleteCustomerUseCase).execute("12333");

    mockMvc.perform(delete("/api/customers/12333")).andExpect(status().isNotFound());

    verify(deleteCustomerUseCase, times(1)).execute("12333");
  }

  @Test
  public void testDeleteCustomer_Exception() throws Exception {
    doThrow(new RuntimeException("Unexpected error")).when(deleteCustomerUseCase).execute("123");

    mockMvc.perform(delete("/api/customers/123")).andExpect(status().isInternalServerError());

    verify(deleteCustomerUseCase, times(1)).execute("123");
  }
}
