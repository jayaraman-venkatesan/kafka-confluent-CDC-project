package com.notification.Customer;

import java.util.List;

public interface CustomerService {

  List<Customer> getAllCustomers();

  List<String> getFailSubscribedEmails();

  List<String> getSuccessSubscribedEmails();

  List<String> getBothSubscribedEmails();

}
