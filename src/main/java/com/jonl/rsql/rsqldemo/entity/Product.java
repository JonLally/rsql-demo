package com.jonl.rsql.rsqldemo.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

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
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid_generator")
  @GenericGenerator(name = "uuid_generator", strategy = "uuid2")
  @Column(updatable = false, insertable = false)
  private String id;
  
  private String name;
  private String description;
  private Double price;

  @ManyToMany(mappedBy = "products")
  private Set<Order> orders;
}
