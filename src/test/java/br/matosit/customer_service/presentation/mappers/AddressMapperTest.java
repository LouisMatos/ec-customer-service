package br.matosit.customer_service.presentation.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import br.matosit.customer_service.domain.entities.Address;
import br.matosit.customer_service.presentation.requests.AddressRequest;

public class AddressMapperTest {

  @Test
  public void testToDomain() {
    AddressRequest request = new AddressRequest("123 Main St", "456", "Apt 789", "Downtown",
        "Springfield", "IL", "62704");

    Address address = AddressMapper.toDomain(request);

    assertEquals("123 Main St", address.getStreet());
    assertEquals("456", address.getNumber());
    assertEquals("Apt 789", address.getComplement());
    assertEquals("Downtown", address.getNeighborhood());
    assertEquals("Springfield", address.getCity());
    assertEquals("IL", address.getState());
    assertEquals("62704", address.getZipCode());
  }
}
