package br.matosit.customer_service.domain.entities;

public class Address {
  private String street;
  private String number;
  private String complement;
  private String neighborhood;
  private String city;
  private String state;
  private String zipCode;

  public Address(String street, String number, String complement, String neighborhood, String city,
      String state, String zipCode) {
    this.street = street;
    this.number = number;
    this.complement = complement;
    this.neighborhood = neighborhood;
    this.city = city;
    this.state = state;
    this.zipCode = zipCode;
  }

  // Getters
  public String getStreet() {
    return street;
  }

  public String getNumber() {
    return number;
  }

  public String getComplement() {
    return complement;
  }

  public String getNeighborhood() {
    return neighborhood;
  }

  public String getCity() {
    return city;
  }

  public String getState() {
    return state;
  }

  public String getZipCode() {
    return zipCode;
  }

  @Override
  public String toString() {
    return "Address [street=" + street + ", number=" + number + ", complement=" + complement
        + ", neighborhood=" + neighborhood + ", city=" + city + ", state=" + state + ", zipCode="
        + zipCode + "]";
  }


}
