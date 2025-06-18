package br.matosit.customer_service.adapters.in.rest.mappers;

import br.matosit.customer_service.adapters.in.rest.requests.AddressRequest;
import br.matosit.customer_service.adapters.in.rest.responses.AddressResponse;
import br.matosit.customer_service.domain.entities.Address;

public class AddressMapper {
  
  public static Address toDomain(AddressRequest request) {
    return new Address(request.street(), request.number(), request.complement(),
        request.neighborhood(), request.city(), request.state(), request.zipCode());
  }
  
  public static AddressRequest toRequest(Address address) {
    return new AddressRequest(address.getStreet(), address.getNumber(), address.getComplement(),
        address.getNeighborhood(), address.getCity(), address.getState(), address.getZipCode());
  }
  
  public static AddressResponse toResponse(Address address) {
    return new AddressResponse(address.getStreet(), address.getNumber(), address.getComplement(),
        address.getNeighborhood(), address.getCity(), address.getState(), address.getZipCode());
  }
}
