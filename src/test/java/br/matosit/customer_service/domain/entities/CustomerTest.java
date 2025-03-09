package br.matosit.customer_service.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Collections;
import org.junit.jupiter.api.Test;

class CustomerTest {

  @Test
  void shouldCreateCustomerSuccessfully() {
    // Arrange
    String name = "João Silva";
    String email = "joao@email.com";
    String phone = "11999999999";
    Address address =
        new Address("Rua Exemplo", "123", "Apto 45", "Centro", "São Paulo", "SP", "01234-567");

    // Act
    Customer customer = new Customer(name, email, phone, Collections.singletonList(address));

    // Assert
    assertNotNull(customer);
    assertEquals(name, customer.getName());
    assertEquals(email, customer.getEmail());
    assertEquals(phone, customer.getPhone());
    assertEquals(1, customer.getAddresses().size());
    assertEquals(address, customer.getAddresses().get(0));
  }

  @Test
  void shouldUpdateEmailSuccessfully() {
    // Arrange
    Customer customer =
        new Customer("João Silva", "joao@email.com", "11999999999", Collections.emptyList());

    String newEmail = "joao.silva@email.com";

    // Act
    customer.updateEmail(newEmail);

    // Assert
    assertEquals(newEmail, customer.getEmail());
  }

  @Test
  void shouldAddNewAddressSuccessfully() {
    // Arrange
    Customer customer =
        new Customer("João Silva", "joao@email.com", "11999999999", Collections.emptyList());

    Address newAddress =
        new Address("Rua Nova", "456", null, "Jardins", "São Paulo", "SP", "04567-890");

    // Act
    customer.addAddress(newAddress);

    // Assert
    assertEquals(1, customer.getAddresses().size());
    assertEquals(newAddress, customer.getAddresses().get(0));
  }

  @Test
  void shouldThrowExceptionWhenUpdatingEmailToEmpty() {
    // Arrange
    Customer customer =
        new Customer("João Silva", "joao@email.com", "11999999999", Collections.emptyList());

    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> {
      customer.updateEmail("");
    });

    assertThrows(IllegalArgumentException.class, () -> {
      customer.updateEmail(null);
    });
  }
}
