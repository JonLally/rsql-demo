package com.jonl.rsql.rsqldemo.entity;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Customer {
  
  public Customer(final String firstName, final String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid_generator")
  @GenericGenerator(name = "uuid_generator", strategy = "uuid2")
  @Column(updatable = false, insertable = false)
  private String id;

  private String firstName;
  private String lastName;
  private LocalDate dob;
  private Integer number;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
  private Set<Order> orders;
}
