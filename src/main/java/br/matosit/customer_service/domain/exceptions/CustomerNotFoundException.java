package br.matosit.customer_service.domain.exceptions;

public class CustomerNotFoundException extends DomainException {

  /**
   * 
   */
  private static final long serialVersionUID = 8078814249958268178L;

  public CustomerNotFoundException(String id) {
    super(String.format("Cliente não encontrado para o id: %s", id), "CUSTOMER-002");
  }


}
