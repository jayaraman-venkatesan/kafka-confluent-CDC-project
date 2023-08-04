package com.notification.customer;

import com.notification.model.Customer;
import com.notification.model.SubscribedEventType;

import java.util.List;

public interface CustomerService {
  List<Customer> getSubscribedCustomersBasedOnEventType(SubscribedEventType subscribedEventType);

}
