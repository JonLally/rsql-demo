package com.jonl.rsql.rsqldemo.rsql;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

public interface SpecificationExecutorController<T> {

  ResponseEntity<CollectionModel<EntityModel<T>>> findAll(Specification<T> spec, Pageable pageable);

  SpecificationConditionalConverter<T> rsqlQueryToSpecificationConverter();
}
