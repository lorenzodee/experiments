# Dynamic Bean Validation

Java's [Bean Validation](http://beanvalidation.org/) is a specification that let's you express constraints on objects via annotations. These annotations can be placed on either the field, or the property (specifically, the property's getter-method). The reference implementation is [Hibernate Validator](http://hibernate.org/validator/).

```java
import javax.validation.constraints.*;

public class Car {

   @NotNull
   private String manufacturer;

   @NotNull
   @Size(min = 2, max = 14)
   private String licensePlate;

   @Min(2)
   private int seatCount;

   // ...
}
```

Unfortunately, this *implies* that the object (or class) needs to be known at compile-time. It is nearly impossible to apply Bean Validation constraints on a dynamic set of values. How can you add constraints to specific entries in a `Map` for example?

This is an experiment in making classes at runtime, specifying constraints on the generated classes, and seeing if constraints are indeed being applied to the instantiated objects of those classes.

Being able to re-use all the constraints available in `javax.validation.constraints` (and all other custom constraints) is reason enough to go through this experiment. Otherwise, we'll end-up *reinventing the wheel*, which we do not want to do.

Please see [DynamicBeanValidationTests](src/test/java/com/orangeandbronze/DynamicBeanValidationTests.java).
