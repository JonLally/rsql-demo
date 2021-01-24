package com.jonl.rsql.rsqldemo.repository;

import com.jonl.rsql.rsqldemo.entity.Product;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface ProductRepository extends PagingAndSortingRepository<Product, String>, JpaSpecificationExecutor<Product> {
  
}
