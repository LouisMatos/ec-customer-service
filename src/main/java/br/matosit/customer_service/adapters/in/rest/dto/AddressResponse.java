package br.matosit.customer_service.adapters.in.rest.dto;

public record AddressResponse(
    String street,
    String number,
    String complement,
    String neighborhood,
    String city,
    String state,
    String zipCode
) {

}
