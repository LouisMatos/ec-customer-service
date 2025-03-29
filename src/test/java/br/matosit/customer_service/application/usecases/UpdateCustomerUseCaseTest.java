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
import org.springframework.dao.DuplicateKeyException;

import br.matosit.customer_service.application.ports.CustomerRepository;
import br.matosit.customer_service.domain.entities.Customer;
import br.matosit.customer_service.domain.exceptions.CustomerNotFoundException;

@ExtendWith(MockitoExtension.class)
class UpdateCustomerUseCaseTest {

  @Mock
  private CustomerRepository customerRepository;

  @InjectMocks
  private UpdateCustomerUseCase updateCustomerUseCase;

  private Customer customer;

  @BeforeEach
  void setUp() {
    customer = new Customer(null, null, null, null);
    customer.setId("123");
    customer.setEmail("test@example.com");
    // Set other customer properties as needed
  }

  @Test
  void testExecute_ValidCustomer_ShouldUpdateCustomer() {
    // Arrange
    when(customerRepository.findById("123")).thenReturn(Optional.of(customer));
    when(customerRepository.findByEmail("test@example.com")).thenReturn(null);
    when(customerRepository.save(any(Customer.class))).thenReturn(customer);

    // Act
    Customer result = updateCustomerUseCase.execute("123", customer);

    // Assert
    assertNotNull(result);
    assertEquals("123", result.getId());
    verify(customerRepository, times(1)).findById("123");
    verify(customerRepository, times(1)).findByEmail("test@example.com");
    verify(customerRepository, times(1)).save(customer);
  }

  @Test
  void testExecute_NonExistentCustomerId_ShouldThrowException() {
    // Arrange
    when(customerRepository.findById("123")).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(CustomerNotFoundException.class, () -> {
      updateCustomerUseCase.execute("123", customer);
    });
    verify(customerRepository, times(1)).findById("123");
    verify(customerRepository, never()).findByEmail(anyString());
    verify(customerRepository, never()).save(any(Customer.class));
  }

  @Test
  void testExecute_DuplicateEmail_ShouldThrowException() {
    // Arrange
    Customer anotherCustomer = new Customer(null, null, null, null);
    anotherCustomer.setId("456");
    anotherCustomer.setEmail("test@example.com");
    when(customerRepository.findById("123")).thenReturn(Optional.of(customer));
    when(customerRepository.findByEmail("test@example.com")).thenReturn(anotherCustomer);

    // Act & Assert
    assertThrows(DuplicateKeyException.class, () -> {
      updateCustomerUseCase.execute("123", customer);
    });
    verify(customerRepository, times(1)).findById("123");
    verify(customerRepository, times(1)).findByEmail("test@example.com");
    verify(customerRepository, never()).save(any(Customer.class));
  }

  
}

