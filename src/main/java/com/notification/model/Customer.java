package com.notification.model;

import java.util.List;

public class Customer {

  private final String name;

  private final String email;

  private final List<SubscribedEventType> subscribedEventTypes;

  public Customer(String name, String email, List<SubscribedEventType> subscribedEventTypes) {
    this.name = name;
    this.email = email;
    this.subscribedEventTypes = subscribedEventTypes;
  }

  public String getEmail() {
    return email;
  }

  public String getName() {
    return name;
  }

  public List<SubscribedEventType> getSubscribedEventTypes() {
    return subscribedEventTypes;
  }


}
