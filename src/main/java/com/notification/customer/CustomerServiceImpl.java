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
    this.customers = new ArrayList<>(Arrays.asList(
            new Customer("I", "clvarsha1998@gmail.com", List.of(SubscribedEventType.Fail)),
            new Customer("J", "clvarsha1998@gmail.com", List.of(SubscribedEventType.Fail)),

            new Customer("K", "jayvenkat1998@gmail.com", List.of(SubscribedEventType.Success)),
            new Customer("L", "jayvenkat1998@gmail.com", List.of(SubscribedEventType.Success)),

            new Customer("M", "jayvenkat1998@gmail.com", List.of(SubscribedEventType.Success, SubscribedEventType.Fail)),
            new Customer("N", "jayvenkat1998@gmail.com", List.of(SubscribedEventType.Success, SubscribedEventType.Fail))

    ));
  }

  public List<Customer> getSubscribedCustomersBasedOnEventType(SubscribedEventType subscribedEventType) {
    return this.customers.stream()
            .filter(customer -> customer.getSubscribedEventTypes().contains(subscribedEventType))
            .collect(Collectors.toList());
  }

  @Override
  public List<Customer> getAllCustomers() {
    return this.customers;
  }
}
