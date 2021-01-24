package com.jonl.rsql.rsqldemo.util;

import java.time.LocalDate;

import com.jonl.rsql.rsqldemo.entity.Customer;
import com.jonl.rsql.rsqldemo.repository.CustomerRepository;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

  private final CustomerRepository customerRepository;

  public DataLoader(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @EventListener
  public void appReady(ApplicationReadyEvent event) {
    customerRepository.save(Customer.builder().firstName("John").lastName("Doe").number(10).dob(LocalDate.of(1990, 10, 23)).build());
    customerRepository.save(Customer.builder().firstName("Jane").lastName("Doe").number(11).dob(LocalDate.of(1991, 5, 16)).build());
    customerRepository.save(Customer.builder().firstName("Joe").lastName("Bloggs").number(12).dob(LocalDate.of(1995, 11, 30)).build());
    customerRepository.save(Customer.builder().firstName("Dave").lastName("Trotter").number(101).dob(LocalDate.of(1979, 4, 20)).build());
    customerRepository.save(Customer.builder().firstName("Rodney").lastName("Trotter").number(102).dob(LocalDate.of(1979, 4, 20)).build());
    customerRepository.save(Customer.builder().firstName("Derek").lastName("Trotter").number(103).dob(LocalDate.of(1970, 12, 25)).build());
  }
}
