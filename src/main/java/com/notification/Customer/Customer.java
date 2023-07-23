package com.notification.Customer;

public class Customer {

  private final String name;

  private final String email;

  private final String subscription;



  public String getEmail() {
    return email;
  }

  public String getSubscription() {
    return subscription;
  }

  Customer(String name , String email , String subscription){
    this.name = name;
    this.email = email;
    this.subscription = subscription;
  }



}
