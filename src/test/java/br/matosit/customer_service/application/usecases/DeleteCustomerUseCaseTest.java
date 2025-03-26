package br.matosit.customer_service.application.usecases;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.matosit.customer_service.application.ports.CustomerRepository;
import br.matosit.customer_service.domain.entities.Customer;
import br.matosit.customer_service.domain.exceptions.CustomerNotFoundException;
import java.util.Optional;

class DeleteCustomerUseCaseTest {

  @Mock
  private CustomerRepository customerRepository;

  @InjectMocks
  private DeleteCustomerUseCase deleteCustomerUseCase;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testExecute_CustomerExists_ShouldDeleteCustomer() {
    // Arrange
    String customerId = "123";
    Customer customer = new Customer(null, null, null, null);
    when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

    // Act
    deleteCustomerUseCase.execute(customerId);

    // Assert
    verify(customerRepository).findById(customerId);
    verify(customerRepository).deleteById(customerId);
  }

  @Test
  void testExecute_CustomerDoesNotExist_ShouldThrowException() {
    // Arrange
    String customerId = "123";
    when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

    // Act & Assert
    CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> {
      deleteCustomerUseCase.execute(customerId);
    });
    assertEquals("Cliente não encontrado para o id: 123", exception.getMessage());
    verify(customerRepository).findById(customerId);
    verify(customerRepository, never()).deleteById(customerId);
  }

}
