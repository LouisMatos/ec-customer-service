package br.matosit.customer_service.adapters.in.rest.requests;

import jakarta.validation.constraints.NotBlank;

public record AddressRequest(@NotBlank(message = "Rua é obrigatória") String street,

    @NotBlank(message = "Número é obrigatório") String number,

    String complement,

    @NotBlank(message = "Bairro é obrigatório") String neighborhood,

    @NotBlank(message = "Cidade é obrigatória") String city,

    @NotBlank(message = "Estado é obrigatório") String state,

    @NotBlank(message = "CEP é obrigatório") String zipCode) {
}
