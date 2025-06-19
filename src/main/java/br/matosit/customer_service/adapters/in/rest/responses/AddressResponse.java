package br.matosit.customer_service.adapters.in.rest.responses;

public record AddressResponse(
    String street,
    String number,
    String complement,
    String neighborhood,
    String city,
    String state,
    String zipCode
) {}
