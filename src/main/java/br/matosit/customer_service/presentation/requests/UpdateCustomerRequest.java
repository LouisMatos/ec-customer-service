package br.matosit.customer_service.presentation.requests;

import java.util.List;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record UpdateCustomerRequest(

    @NotBlank(message = "Nome é obrigatório") String name,

    @NotBlank(message = "Email é obrigatório") @Email(message = "Email inválido") String email,

    @NotBlank(message = "Telefone é obrigatório") String phone,

    @NotEmpty(message = "Pelo menos um endereço é obrigatório") List<AddressRequest> addresses

) {
}
