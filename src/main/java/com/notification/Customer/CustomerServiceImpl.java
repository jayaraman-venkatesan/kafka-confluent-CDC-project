package com.notification.Customer;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService{

  private final List<Customer> customers;

  public CustomerServiceImpl() {
    this.customers = new ArrayList<>(Arrays.asList(
        new Customer("I","","F"),
        new Customer("J","","F"),

        new Customer("K","","S"),
        new Customer("L","","S"),

        new Customer("M","","B"),
        new Customer("N","","B")

    ));


  }

  private List<String> filterEmail(String subscription){

    return this.customers.stream()
        .filter(customer -> subscription.equals(customer.getSubscription()))
        .map(Customer::getEmail)
        .collect(Collectors.toList());

  }

  @Override
  public List<Customer> getAllCustomers() {
    return this.customers;
  }

  @Override
  public List<String> getFailSubscribedEmails() {
    return filterEmail("F");
  }

  @Override
  public List<String> getSuccessSubscribedEmails() {

    return filterEmail("S");

  }

  @Override
  public List<String> getBothSubscribedEmails() {
    return filterEmail("B");
  }
}
