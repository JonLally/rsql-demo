package com.jonl.rsql.rsqldemo.repository;

import com.jonl.rsql.rsqldemo.entity.Order;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface OrderRepository extends PagingAndSortingRepository<Order, String>, JpaSpecificationExecutor<Order> {
  
}
