package com.jonl.rsql.rsqldemo.configuration;

import java.util.List;

import com.jonl.rsql.rsqldemo.rsql.SpecificationExecutorController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class RSQLRepositoryRestConfiguration implements RepositoryRestConfigurer {

  @Autowired
  private List<SpecificationExecutorController<?>> rsqlSpecificationExecutorControllers;

  @Override
  public void configureConversionService(final ConfigurableConversionService configurableConversionService) {
    rsqlSpecificationExecutorControllers.stream().forEach(
        controller -> configurableConversionService.addConverter(controller.rsqlQueryToSpecificationConverter()));
  }

  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
    log.info("Disabling default exposure of REST Repositories");
    config.disableDefaultExposure();
  }

  @Bean
  @Primary
  public HateoasPageableHandlerMethodArgumentResolver customResolver(
      HateoasPageableHandlerMethodArgumentResolver pageableResolver) {
    pageableResolver.setOneIndexedParameters(true);
    pageableResolver.setFallbackPageable(PageRequest.of(0, Integer.MAX_VALUE));
    pageableResolver.setMaxPageSize(Integer.MAX_VALUE);
    return pageableResolver;
  }
}
