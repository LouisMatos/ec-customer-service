package br.matosit.customer_service.domain.exceptions;

public class CustomerAlreadyExistsException extends DomainException {
  /**
  * 
  */
  private static final long serialVersionUID = -6248244341504055174L;

  public CustomerAlreadyExistsException(String email) {
    super(String.format("Cliente já existe com o email: %s", email), "CUSTOMER-001");
  }
}
