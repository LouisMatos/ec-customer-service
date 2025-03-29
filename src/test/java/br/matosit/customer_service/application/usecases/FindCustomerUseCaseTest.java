package br.matosit.customer_service.application.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.matosit.customer_service.application.ports.CustomerRepository;
import br.matosit.customer_service.domain.entities.Customer;
import br.matosit.customer_service.domain.exceptions.CustomerNotFoundException;

@ExtendWith(MockitoExtension.class)
class FindCustomerUseCaseTest {

  @Mock
  private CustomerRepository customerRepository;

  @InjectMocks
  private FindCustomerUseCase findCustomerUseCase;

  private Customer customer;

  @BeforeEach
  void setUp() {
    customer = new Customer(null, null, null, null);
    customer.setId("123");
    // Set other customer properties as needed
  }

  @Test
  void testExecute_ValidCustomerId_ShouldReturnCustomer() {
    // Arrange
    when(customerRepository.findById("123")).thenReturn(Optional.of(customer));

    // Act
    Customer result = findCustomerUseCase.execute("123");

    // Assert
    assertNotNull(result);
    assertEquals("123", result.getId());
    verify(customerRepository, times(1)).findById("123");
  }

  @Test
  void testExecute_NonExistentCustomerId_ShouldThrowException() {
    // Arrange
    when(customerRepository.findById("123")).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(CustomerNotFoundException.class, () -> {
      findCustomerUseCase.execute("123");
    });
    verify(customerRepository, times(1)).findById("123");
  }


}
