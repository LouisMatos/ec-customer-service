package br.matosit.customer_service.adapters.in.rest.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorResponse {

  private String code;
  private String message;
  private Map<String, List<String>> validationErrors = new HashMap<>();

  private ErrorResponse(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public static ErrorResponse of(String code, String message) {
    return new ErrorResponse(code, message);
  }

  public void addValidationError(String field, String message) {
    this.validationErrors.computeIfAbsent(field, k -> new ArrayList<>()).add(message);
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public Map<String, List<String>> getValidationErrors() {
    return validationErrors;
  }
}
