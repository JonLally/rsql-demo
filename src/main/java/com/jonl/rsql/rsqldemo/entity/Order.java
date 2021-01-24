package com.jonl.rsql.rsqldemo.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

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
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid_generator")
  @GenericGenerator(name = "uuid_generator", strategy = "uuid2")
  @Column(updatable = false, insertable = false)
  private String id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "CUSTOMER_ID", nullable = false, updatable = false)
  private Customer customer;

  @ManyToMany
  @JoinTable(name = "PRODUCT_ORDERS")
  private Set<Product> products;

  private Double discount;

  @Transient
  private Double total;

  @Transient
  private Double totalMinusDiscount;

}
