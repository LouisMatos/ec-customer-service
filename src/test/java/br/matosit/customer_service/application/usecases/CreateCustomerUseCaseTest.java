package br.matosit.customer_service.application.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.matosit.customer_service.application.ports.CustomerRepository;
import br.matosit.customer_service.domain.entities.Customer;
import br.matosit.customer_service.domain.exceptions.CustomerAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CreateCustomerUseCaseTest {

  @Mock
  private CustomerRepository customerRepository;

  @InjectMocks
  private CreateCustomerUseCase createCustomerUseCase;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testExecute_CustomerDoesNotExist_ShouldCreateCustomer() {
    // Arrange
    Customer customer = new Customer("test@example.com", "Test", "User", null);
    when(customerRepository.findByEmail(customer.getEmail())).thenReturn(null);
    when(customerRepository.save(customer)).thenReturn(customer);

    // Act
    Customer createdCustomer = createCustomerUseCase.execute(customer);

    // Assert
    assertNotNull(createdCustomer);
    assertEquals(customer.getEmail(), createdCustomer.getEmail());
    verify(customerRepository).findByEmail(customer.getEmail());
    verify(customerRepository).save(customer);
  }

  @Test
  void testExecute_CustomerAlreadyExists_ShouldThrowException() {
    // Arrange
    Customer customer = new Customer("test@example.com", "Test", "User", null);
    when(customerRepository.findByEmail(customer.getEmail())).thenReturn(customer);

    // Act & Assert
    assertThrows(CustomerAlreadyExistsException.class, () -> {
      createCustomerUseCase.execute(customer);
    });
    verify(customerRepository).findByEmail(customer.getEmail());
    verify(customerRepository, never()).save(any(Customer.class));
  }

  @Test
  void testExecute_RepositoryThrowsException_ShouldPropagateException() {
    // Arrange
    Customer customer = new Customer("test@example.com", "Test", "User", null);
    when(customerRepository.findByEmail(customer.getEmail())).thenReturn(null);
    when(customerRepository.save(customer)).thenThrow(new RuntimeException("Database error"));

    // Act & Assert
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      createCustomerUseCase.execute(customer);
    });
    assertEquals("Database error", exception.getMessage());
    verify(customerRepository).findByEmail(customer.getEmail());
    verify(customerRepository).save(customer);
  }

  @Test
  void testExecute_CustomerSavedWithCorrectData() {
    // Arrange
    Customer customer = new Customer("Test", "test@example.com", "User", null);
    when(customerRepository.findByEmail(customer.getEmail())).thenReturn(null);
    when(customerRepository.save(customer)).thenReturn(customer);

    // Act
    Customer createdCustomer = createCustomerUseCase.execute(customer);

    // Assert
    assertNotNull(createdCustomer);
    assertEquals("test@example.com", createdCustomer.getEmail());
    assertEquals("Test", createdCustomer.getName());
    verify(customerRepository).findByEmail(customer.getEmail());
    verify(customerRepository).save(customer);
  }



}

