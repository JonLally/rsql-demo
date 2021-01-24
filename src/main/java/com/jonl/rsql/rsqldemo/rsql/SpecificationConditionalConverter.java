package com.jonl.rsql.rsqldemo.rsql;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.jpa.domain.Specification;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;

public class SpecificationConditionalConverter<T> implements Converter<String, Specification<T>>, ConditionalConverter {

  private final Class<T> entityTypeParameterClass;

  public SpecificationConditionalConverter(final Class<T> entityTypeParameterClass) {
    this.entityTypeParameterClass = entityTypeParameterClass;
  }

  @Override
  public Specification<T> convert(String source) {
    final Node rootNode = new RSQLParser().parse(source);
    return rootNode.accept(new RSQLVisitorImpl<T>());
  }

  @Override
  public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
    if (targetType.getResolvableType().hasGenerics()) {
      return targetType.getResolvableType().getGeneric(0).isAssignableFrom(entityTypeParameterClass);
    }
    return false;
  }
}
