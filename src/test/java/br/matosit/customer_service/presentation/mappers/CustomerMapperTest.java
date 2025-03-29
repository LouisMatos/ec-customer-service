package br.matosit.customer_service.presentation.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;
import br.matosit.customer_service.domain.entities.Address;
import br.matosit.customer_service.domain.entities.Customer;
import br.matosit.customer_service.presentation.requests.AddressRequest;
import br.matosit.customer_service.presentation.requests.CreateCustomerRequest;
import br.matosit.customer_service.presentation.requests.UpdateCustomerRequest;

public class CustomerMapperTest {

  @Test
  public void testToDomain_CreateCustomerRequest() {
    CreateCustomerRequest request = new CreateCustomerRequest("John Doe", "john.doe@example.com",
        "123-456-7890", List.of(new AddressRequest("123 Main St", "456", "Apt 789", "Downtown",
            "Springfield", "IL", "62704")));

    Customer customer = CustomerMapper.toDomain(request);

    assertEquals("John Doe", customer.getName());
    assertEquals("john.doe@example.com", customer.getEmail());
    assertEquals("123-456-7890", customer.getPhone());
    assertEquals(1, customer.getAddresses().size());
    Address address = customer.getAddresses().get(0);
    assertEquals("123 Main St", address.getStreet());
    assertEquals("456", address.getNumber());
    assertEquals("Apt 789", address.getComplement());
    assertEquals("Downtown", address.getNeighborhood());
    assertEquals("Springfield", address.getCity());
    assertEquals("IL", address.getState());
    assertEquals("62704", address.getZipCode());
  }

  @Test
  public void testToDomain_UpdateCustomerRequest() {
    UpdateCustomerRequest request = new UpdateCustomerRequest("Jane Doe", "jane.doe@example.com",
        "098-765-4321", List.of(new AddressRequest("456 Elm St", "789", "Suite 101", "Uptown",
            "Shelbyville", "IL", "62705")));

    Customer customer = CustomerMapper.toDomain(request);

    assertEquals("Jane Doe", customer.getName());
    assertEquals("jane.doe@example.com", customer.getEmail());
    assertEquals("098-765-4321", customer.getPhone());
    assertEquals(1, customer.getAddresses().size());
    Address address = customer.getAddresses().get(0);
    assertEquals("456 Elm St", address.getStreet());
    assertEquals("789", address.getNumber());
    assertEquals("Suite 101", address.getComplement());
    assertEquals("Uptown", address.getNeighborhood());
    assertEquals("Shelbyville", address.getCity());
    assertEquals("IL", address.getState());
    assertEquals("62705", address.getZipCode());
  }
}
