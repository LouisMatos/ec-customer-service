package br.matosit.customer_service.presentation.mappers;

import br.matosit.customer_service.domain.entities.Address;
import br.matosit.customer_service.presentation.requests.AddressRequest;

public class AddressMapper {
    public static Address toDomain(AddressRequest request) {
        return new Address(
            request.street(),
            request.number(),
            request.complement(),
            request.neighborhood(),
            request.city(),
            request.state(),
            request.zipCode()
        );
    }
} 