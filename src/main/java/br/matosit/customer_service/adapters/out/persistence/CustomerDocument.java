package br.matosit.customer_service.adapters.out.persistence;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customers")
public class CustomerDocument {

  @Id
  private String id;
  private String name;
  @Indexed(unique = true)
  private String email;
  private String phone;
  private List<AddressDocument> addresses;

  public CustomerDocument() {
  }

  public CustomerDocument(String id, String name, String email, String phone,
      List<AddressDocument> addresses) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.phone = phone;
    this.addresses = addresses;
  }

  // Getters and setters
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public List<AddressDocument> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<AddressDocument> addresses) {
    this.addresses = addresses;
  }
}

