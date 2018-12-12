# Personal practice of examples in book "Spring in Action, 5th edition"
Run app with `mvn spring-boot:run`

Run test with `mvn test`

Run specific test with e.g. `mvn -Dtest=HomeControllerTest test`

H2 DB console
```
  http://localhost:8080/h2-console
```
JDBC URL sets to `jdbc:h2:mem:testdb`

What is Spring?
---
At its core, Spring offers a `container`, often referred to as the `Spring application context`, that creates and manages application components. 
These components, or `beans`, are ***wired together*** inside the Spring application context to make a complete application, 
much like bricks, mortar, timber, nails, plumbing, and wiring are bound together to make a house. 

The act of wiring beans together is based on a pattern known as `dependency injection` (DI). 
Rather than have components create and maintain the lifecycle of other beans that they depend on, 
a dependency-injected application relies on a separate entity (the container) to create and maintain 
all components and inject those into the beans that need them. This is done typically through constructor arguments or property accessor methods.

Automatic configuration has its roots in the Spring techniques known as `autowiring` and `component scanning`. 
1) With `component scanning`, Spring can automatically discover components from an application’s classpath and create them as beans in the Spring application context. 
2) With `autowiring`, Spring automatically injects the components with the other beans that they depend on.

More recently, with the introduction of `Spring Boot`, automatic configuration has gone well beyond `component scanning` and `autowiring`. 
Spring Boot is an extension of the Spring Framework that offers several productivity ***enhancements***. The most well-known of these enhancements is 
`autoconfiguration`, where Spring Boot can make ***reasonable guesses*** of what components need to be configured and wired together, 
based on entries in the classpath, environment variables, and other factors.

Annotation *@Configuration*
---
In recent versions of Spring, however, a Java-based configuration is more common. The following Java-based configuration class is equivalent to the XML configuration:
```
@Configuration
public class ServiceConfiguration {
  @Bean
  public InventoryService inventoryService() {
    return new InventoryService();
  }
 
  @Bean
  public ProductService productService() {
    return new ProductService(inventoryService());
  }
}
```
The @Configuration annotation indicates to Spring that this is a configuration class 
that will provide beans to the Spring application context. The configuration’s class methods 
are annotated with @Bean, indicating that ***the objects they return*** should be ***added as beans*** 
in the application context (where, by default, their respective bean IDs will 
be the same as the names of the methods that define them).

