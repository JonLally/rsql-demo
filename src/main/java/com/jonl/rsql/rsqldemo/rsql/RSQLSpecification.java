package com.jonl.rsql.rsqldemo.rsql;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RSQLSpecification<T> implements Specification<T> {

  private String property;
  private ComparisonOperator operator;
  private List<String> arguments;

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    List<Object> args = castArguments(root);
    Object argument = args.get(0);
    switch (RSQLSearchOperation.getSimpleOperator(operator)) {
      case EQUAL:
        if (argument instanceof String) {
          return builder.like(getAbsolutePath(root, property), argument.toString().replace('*', '%'));
        } else if (argument == null) {
          return builder.isNull(getAbsolutePath(root, property));
        } else {
          return builder.equal(getAbsolutePath(root, property), argument);
        }
      case NOT_EQUAL:
        if (argument instanceof String) {
          return builder.notLike(root.<String>get(property), argument.toString().replace('*', '%'));
        } else if (argument == null) {
          return builder.isNotNull(getAbsolutePath(root, property));
        } else {
          return builder.notEqual(getAbsolutePath(root, property), argument);
        }
      case GREATER_THAN:
        return builder.greaterThan(root.<String>get(property), argument.toString());
      case GREATER_THAN_OR_EQUAL:
        return builder.greaterThanOrEqualTo(root.<String>get(property), argument.toString());
      case LESS_THAN:
        return builder.lessThan(root.<String>get(property), argument.toString());
      case LESS_THAN_OR_EQUAL:
        return builder.lessThanOrEqualTo(root.<String>get(property), argument.toString());
      case IN:
        return getAbsolutePath(root, property).in(args);
      case NOT_IN:
        return builder.not(getAbsolutePath(root, property).in(args));
      default:
        return null;
    }
  }

  /**
   * Handles embedded classes and IDs where they must be specified by their field
   * name in the root and then their field name in the embedded class spearated
   * with ".".
   */
  private Path<String> getAbsolutePath(final Path<?> path, final String property) {
    if (property.contains(".")) {
      return getAbsolutePath(path.get(property.substring(0, property.indexOf("."))),
          property.substring(property.indexOf(".") + 1));
    }
    return path.get(property);
  }

  private List<Object> castArguments(final Root<T> root) {
    Class<? extends Object> type = getAbsolutePath(root, property).getJavaType();
    return arguments.stream().map(arg -> {
      if (type.equals(Integer.class)) {
        return Integer.parseInt(arg);
      } else if (type.equals(Long.class)) {
        return Long.parseLong(arg);
      } else {
        return arg;
      }
    }).collect(Collectors.toList());
  }

}
