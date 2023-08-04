package com.notification.customer;


import com.notification.model.Customer;
import com.notification.model.SubscribedEventType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

  private final List<Customer> customers;

  public CustomerServiceImpl() {
    // creating dummy customers
    this.customers = new ArrayList<>(Arrays.asList(
            new Customer("a", "a@gmail.edu", List.of(SubscribedEventType.Success)),
            new Customer("b", "b+2@gmail.com", List.of(SubscribedEventType.Success, SubscribedEventType.Fail))

    ));
  }

  public List<Customer> getSubscribedCustomersBasedOnEventType(SubscribedEventType subscribedEventType) {
    return this.customers.stream()
            .filter(customer -> customer.getSubscribedEventTypes().contains(subscribedEventType))
            .collect(Collectors.toList());
  }
}
