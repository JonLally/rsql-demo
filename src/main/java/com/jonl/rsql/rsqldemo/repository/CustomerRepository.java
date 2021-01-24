package com.jonl.rsql.rsqldemo.repository;

import java.util.List;

import com.jonl.rsql.rsqldemo.entity.Customer;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CustomerRepository extends PagingAndSortingRepository<Customer, String>, JpaSpecificationExecutor<Customer> {
  
}
