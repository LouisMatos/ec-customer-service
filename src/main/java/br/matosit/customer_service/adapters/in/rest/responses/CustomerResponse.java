package br.matosit.customer_service.adapters.in.rest.responses;

import java.util.List;

public record CustomerResponse(String id, String name, String email, String phone,
    List<AddressResponse> addresses) {
}
