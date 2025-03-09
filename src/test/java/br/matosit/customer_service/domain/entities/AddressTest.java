package br.matosit.customer_service.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class AddressTest {

    @Test
    void shouldCreateAddressSuccessfully() {
        // Arrange
        String street = "Rua Exemplo";
        String number = "123";
        String complement = "Apto 45";
        String neighborhood = "Centro";
        String city = "São Paulo";
        String state = "SP";
        String zipCode = "01234-567";

        // Act
        Address address = new Address(street, number, complement, neighborhood, city, state, zipCode);

        // Assert
        assertNotNull(address);
        assertEquals(street, address.getStreet());
        assertEquals(number, address.getNumber());
        assertEquals(complement, address.getComplement());
        assertEquals(neighborhood, address.getNeighborhood());
        assertEquals(city, address.getCity());
        assertEquals(state, address.getState());
        assertEquals(zipCode, address.getZipCode());
    }

    @Test
    void shouldCreateAddressWithoutComplement() {
        // Arrange
        String street = "Rua Exemplo";
        String number = "123";
        String neighborhood = "Centro";
        String city = "São Paulo";
        String state = "SP";
        String zipCode = "01234-567";

        // Act
        Address address = new Address(street, number, null, neighborhood, city, state, zipCode);

        // Assert
        assertNotNull(address);
        assertEquals(street, address.getStreet());
        assertEquals(number, address.getNumber());
        assertNull(address.getComplement());
        assertEquals(neighborhood, address.getNeighborhood());
        assertEquals(city, address.getCity());
        assertEquals(state, address.getState());
        assertEquals(zipCode, address.getZipCode());
    }
} 