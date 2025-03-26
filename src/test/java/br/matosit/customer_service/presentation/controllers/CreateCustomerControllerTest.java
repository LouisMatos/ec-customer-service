package br.matosit.customer_service.presentation.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.matosit.customer_service.application.usecases.CreateCustomerUseCase;
import br.matosit.customer_service.domain.entities.Address;
import br.matosit.customer_service.domain.entities.Customer;
import br.matosit.customer_service.infrastructure.persistence.MongoCustomerRepository;
import br.matosit.customer_service.presentation.requests.AddressRequest;
import br.matosit.customer_service.presentation.requests.CreateCustomerRequest;

@WebMvcTest(CreateCustomerController.class)
public class CreateCustomerControllerTest {

  @MockBean
  private CreateCustomerUseCase createCustomerUseCase;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private MongoCustomerRepository mongoCustomerRepository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testCreateCustomer() throws Exception {
    CreateCustomerRequest request = new CreateCustomerRequest("John Doe", "john.doe@example.com",
        "123-456-7890", List.of(new AddressRequest("123 Main St", "456", "Apt 789", "Downtown",
            "Springfield", "IL", "62704")));

    Customer customer = new Customer(null, null, null, null);
    customer.setId("1L");
    customer.setName("John Doe");
    customer.setEmail("john.doe@example.com");
    customer.setPhone("123-456-7890");
    customer.setAddresses(List.of(
        new Address("123 Main St", "456", "Apt 789", "Downtown", "Springfield", "IL", "62704")));

    when(createCustomerUseCase.execute(any(Customer.class))).thenReturn(customer);
    when(mongoCustomerRepository.save(any(Customer.class))).thenReturn(customer);

    mockMvc
        .perform(post("/api/customers").contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(request)))
        .andExpect(status().isOk()).andExpect(jsonPath("$.id").value("1L"))
        .andExpect(jsonPath("$.name").value("John Doe"))
        .andExpect(jsonPath("$.email").value("john.doe@example.com"))
        .andExpect(jsonPath("$.phone").value("123-456-7890"))
        .andExpect(jsonPath("$.addresses[0].street").value("123 Main St"))
        .andExpect(jsonPath("$.addresses[0].number").value("456"))
        .andExpect(jsonPath("$.addresses[0].complement").value("Apt 789"))
        .andExpect(jsonPath("$.addresses[0].neighborhood").value("Downtown"))
        .andExpect(jsonPath("$.addresses[0].city").value("Springfield"))
        .andExpect(jsonPath("$.addresses[0].state").value("IL"))
        .andExpect(jsonPath("$.addresses[0].zipCode").value("62704"));
  }

  @Test
  public void testCreateCustomer_InvalidEmail() throws Exception {
    CreateCustomerRequest request = new CreateCustomerRequest("John Doe", "invalid-email",
        "123-456-7890", List.of(new AddressRequest("123 Main St", "456", "Apt 789", "Downtown",
            "Springfield", "IL", "62704")));

    mockMvc
        .perform(post("/api/customers").contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  

  @Test
  public void testCreateCustomer_UseCaseException() throws Exception {
    CreateCustomerRequest request = new CreateCustomerRequest("John Doe", "john.doe@example.com",
        "123-456-7890", List.of(new AddressRequest("123 Main St", "456", "Apt 789", "Downtown",
            "Springfield", "IL", "62704")));

    when(createCustomerUseCase.execute(any(Customer.class)))
        .thenThrow(new RuntimeException("Error"));

    mockMvc
        .perform(post("/api/customers").contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(request)))
        .andExpect(status().isInternalServerError());
  }

  @Test
  public void testCreateCustomer_EmptyAddress() throws Exception {
    CreateCustomerRequest request =
        new CreateCustomerRequest("John Doe", "john.doe@example.com", "123-456-7890", List.of());

    mockMvc
        .perform(post("/api/customers").contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testCreateCustomer_EmptyName() throws Exception {
    CreateCustomerRequest request = new CreateCustomerRequest("", "john.doe@example.com",
        "123-456-7890", List.of(new AddressRequest("123 Main St", "456", "Apt 789", "Downtown",
            "Springfield", "IL", "62704")));

    mockMvc
        .perform(post("/api/customers").contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }


}
