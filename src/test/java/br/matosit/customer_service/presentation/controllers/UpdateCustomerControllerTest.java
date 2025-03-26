package br.matosit.customer_service.presentation.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.matosit.customer_service.application.usecases.UpdateCustomerUseCase;
import br.matosit.customer_service.infrastructure.persistence.MongoCustomerRepository;

@WebMvcTest(UpdateCustomerController.class)
@ExtendWith(MockitoExtension.class)
public class UpdateCustomerControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UpdateCustomerUseCase updateCustomerUseCase;

  @MockBean
  private MongoCustomerRepository mogoCustomerRepository;

  private ObjectMapper objectMapper;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    objectMapper = new ObjectMapper();
  }

 
}
