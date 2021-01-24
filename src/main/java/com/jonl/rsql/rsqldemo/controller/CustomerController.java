package com.jonl.rsql.rsqldemo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.jonl.rsql.rsqldemo.entity.Customer;
import com.jonl.rsql.rsqldemo.repository.CustomerRepository;
import com.jonl.rsql.rsqldemo.rsql.SpecificationConditionalConverter;
import com.jonl.rsql.rsqldemo.rsql.SpecificationExecutorController;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RepositoryRestController
@RequestMapping("/customer")
public class CustomerController implements SpecificationExecutorController<Customer> {

  private final CustomerRepository customerRepository;

  public CustomerController(final CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  @GetMapping
  public ResponseEntity<CollectionModel<EntityModel<Customer>>> findAll(
      @RequestParam(name = "spec", required = false) Specification<Customer> spec, final Pageable pageable) {
    log.info("Received request");
    final Sort sort = pageable == null ? Sort.unsorted() : pageable.getSort();
    if (pageable == null || pageable.getPageSize() == Integer.MAX_VALUE) {
      final List<Customer> customers = customerRepository.findAll(spec, sort);
      return ResponseEntity.ok(CollectionModel.of(toModel(customers)));
    } else {
      final Page<Customer> customers = customerRepository.findAll(spec, pageable);
      return ResponseEntity.ok(PagedModel.of(toModel(customers),
          new PageMetadata(pageable.getPageSize(), customers.getNumber(), customers.getTotalElements())));
    }
    // , linkTo(methodOn(CustomerController.class).findAll(spec)).withSelfRel()));
    // requires implementation of a Specification to String converter
  }

  @Override
  public SpecificationConditionalConverter<Customer> rsqlQueryToSpecificationConverter() {
    return new SpecificationConditionalConverter<>(Customer.class);
  }

  private List<EntityModel<Customer>> toModel(final Iterable<Customer> customers) {
    return StreamSupport.stream(customers.spliterator(), false).map(
        customer -> EntityModel.of(customer, linkTo(CustomerController.class).slash(customer.getId()).withSelfRel()))
        .collect(Collectors.toList());
  }
}
