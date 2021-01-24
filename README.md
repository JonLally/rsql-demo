### RSQL example

The goal of this project is to find a way to use RSQL alongside Spring Data REST and compliment the findAll (GET /{repository}) with some of the JpaSpecificationExecutor methods. Possibly the best description of what this demo does can be found in the [tests](src/test/java/com/jonl/rsql/rsqldemo/RsqlDemoApplicationTests.java). 

There are a number of parts to this that require more thought and this is just a basic working solution. Exposing this kind of behavior in an application should be done with great care as anybody could potentially query for any field in an entity.

### Reference Documentation
Here are some references that helped me greatly to understand and implement this demo:

* [Rest Repositories](https://docs.spring.io/spring-boot/docs/2.4.2/reference/htmlsingle/#howto-use-exposing-spring-data-repositories-rest-endpoint)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.4.2/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Magic of Spring Data](https://dzone.com/articles/magic-of-spring-data)
* [Spring Conversion Service for target type optional](https://stackoverflow.com/questions/49390639/defining-multiple-spring-converters-with-target-type-optional#) - Very useful in finding solution for Specification<T> converter
* [rsql-parser](https://github.com/jirutka/rsql-parser)
* [REST API search language RSQL FIQL](https://www.baeldung.com/rest-api-search-language-rsql-fiql)

