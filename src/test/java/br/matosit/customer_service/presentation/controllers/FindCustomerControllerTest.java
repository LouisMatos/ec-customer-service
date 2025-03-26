package br.matosit.customer_service.presentation.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import br.matosit.customer_service.application.usecases.FindCustomerUseCase;
import br.matosit.customer_service.domain.entities.Customer;
import br.matosit.customer_service.domain.exceptions.CustomerNotFoundException;
import br.matosit.customer_service.infrastructure.persistence.MongoCustomerRepository;

@WebMvcTest(FindCustomerController.class)
@ExtendWith(MockitoExtension.class)
public class FindCustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FindCustomerUseCase findCustomerUseCase;

    @MockBean
    private MongoCustomerRepository mogoCustomerRepository;

    @BeforeEach
    public void setUp() {
      MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCustomer_Success() throws Exception {
        Customer customer = new Customer(null, null, null, null);
        customer.setId("123");
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPhone("123-456-7890");

        when(findCustomerUseCase.execute("123")).thenReturn(customer);

        mockMvc.perform(get("/api/customers/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.phone").value("123-456-7890"));

        verify(findCustomerUseCase, times(1)).execute("123");
    }

    @Test
    public void testGetCustomer_NotFound() throws Exception {
        when(findCustomerUseCase.execute("123")).thenThrow(new CustomerNotFoundException("123"));

        mockMvc.perform(get("/api/customers/123"))
                .andExpect(status().isNotFound());

        verify(findCustomerUseCase, times(1)).execute("123");
    }

    @Test
    public void testGetCustomer_Exception() throws Exception {
        when(findCustomerUseCase.execute("123")).thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(get("/api/customers/123"))
                .andExpect(status().isInternalServerError());

        verify(findCustomerUseCase, times(1)).execute("123");
    }
}
