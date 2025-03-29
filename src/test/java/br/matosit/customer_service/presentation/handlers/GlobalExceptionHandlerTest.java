package br.matosit.customer_service.presentation.handlers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import br.matosit.customer_service.domain.exceptions.CustomerAlreadyExistsException;
import br.matosit.customer_service.domain.exceptions.DomainException;
import br.matosit.customer_service.presentation.responses.ErrorResponse;

import java.util.Collections;

class GlobalExceptionHandlerTest {

  private GlobalExceptionHandler globalExceptionHandler;

  @BeforeEach
  void setUp() {
    globalExceptionHandler = new GlobalExceptionHandler();
  }

  @Test
  void testHandleCustomerAlreadyExists() {
    // Arrange
    CustomerAlreadyExistsException ex = new CustomerAlreadyExistsException("test@example.com");

    // Act
    ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleCustomerAlreadyExists(ex);

    // Assert
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    assertEquals("CUSTOMER-001", response.getBody().getCode());
    assertEquals("Cliente já existe com o email: test@example.com",
        response.getBody().getMessage());
  }

  @Test
  void testHandleDomainException() {
    // Arrange
    class TestDomainException extends DomainException {
      private static final long serialVersionUID = 1L;

      public TestDomainException(String message, String code) {
        super(message, code);
      }
    }
    DomainException ex = new TestDomainException("Domain error", "DOMAIN-001");

    // Act
    ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleDomainException(ex);

    // Assert
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("DOMAIN-001", response.getBody().getCode());
    assertEquals("Domain error", response.getBody().getMessage());
  }

  @Test
  void testHandleValidationExceptions() {
    // Arrange
    FieldError fieldError = new FieldError("objectName", "field", "defaultMessage");
    BindingResult bindingResult = mock(BindingResult.class);
    when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(fieldError));
    MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

    // Act
    ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleValidationExceptions(ex);

    // Assert
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("VALIDATION-001", response.getBody().getCode());
    assertEquals("Erro de validação nos campos", response.getBody().getMessage());
    assertEquals("Erro de validação nos campos", response.getBody().getMessage());
    assertEquals("VALIDATION-001", response.getBody().getCode());
  }

  @Test
  void testHandleGenericException() {
    // Arrange
    Exception ex = new Exception("Internal server error");

    // Act
    ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGenericException(ex);

    // Assert
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("INTERNAL-001", response.getBody().getCode());
    assertEquals("Ocorreu um erro interno no servidor", response.getBody().getMessage());
  }
}
