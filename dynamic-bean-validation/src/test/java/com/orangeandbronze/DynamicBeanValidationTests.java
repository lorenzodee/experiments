package com.orangeandbronze;

import static org.junit.Assert.*;

import java.lang.annotation.ElementType;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.hibernate.validator.cfg.defs.MinDef;
import org.hibernate.validator.cfg.defs.NotNullDef;
import org.hibernate.validator.cfg.defs.SizeDef;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * <p>
 * This test is similar to {@link ProgrammaticBeanValidationTests}. But this
 * time, the bean class <em>does not</em> exist at compile-time. It will be
 * defined at runtime (for cases when the properties cannot be determined at
 * compile-time). The tests verify if the constraints are indeed being applied.
 * </p>
 * <p>
 * In this test, we generate a class (at runtime) that is roughly equivalent to:
 * </p>
 * 
 * <pre>
 * public class FooBar {
 * 	&#064;NotNull &#064;Min(2)
 * 	private Integer foo;
 *
 * 	&#064;NotNull &#064;Size(min=2, max=14)
 * 	private String bar;
 *
 * 	public Integer getFoo() {...}
 * 	public void setFoo(Integer foo) {...}
 *
 * 	public String getBar() {...}
 * 	public void setBar(String bar) {...}
 * }
 * </pre>
 * <p>
 * <strong>WARNING:</strong> This uses Hibernate Validator's <a href=
 * "http://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#section-programmatic-api">
 * programmatic constraint configuration API</a>, which makes it unportable (not
 * portable) to other Bean Validation implementations.
 * </p>
 *
 * @see ProgrammaticBeanValidationTests
 */
public class DynamicBeanValidationTests {

	private static ValidatorFactory validatorFactory;
	private static Validator validator;
	private static Class<?> generatedClazz;
	private static BeanClassFactory beanClassFactory;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		HibernateValidatorConfiguration configuration;
		configuration = Validation
				.byProvider(HibernateValidator.class)
				.configure();
		ConstraintMapping constraintMapping = configuration.createConstraintMapping();

		// NOTE: Use GenericConstraintDef<> for custom constraints

		// TODO What if we create ConstraintMapping based on values retrieved via JDBC?
		// List<ConstraintMapping> getContraintMappings()

		// Define a JavaBean at runtime and add Bean Validation annotations
		Map<String, Class<?>> properties = new HashMap<String, Class<?>>();
		// Add foo property (java.lang.Integer)
		properties.put("foo", Integer.class);
		// Add bar property (java.lang.String)
		properties.put("bar", String.class);

		beanClassFactory = new JavassistBeanClassFactory();

		generatedClazz = beanClassFactory.createBeanClass(
				"com.orangeandbronze.BeanClassFactory$FooBar", properties);

		// Add constraints to "runtime-generated" class
		constraintMapping
			.type(generatedClazz)
				// .constraint(definition) // for class-level constraints
				// @NotNull @Min(2) private Integer foo;
				.property("foo", ElementType.FIELD)
					.constraint(new NotNullDef())
					.constraint(new MinDef().value(2))
				// @NotNull @Size(min=2, max=14) private Integer foo;
				.property("bar", ElementType.FIELD)
					.constraint(new NotNullDef())
					.constraint(new SizeDef().min(2).max(14));

		// TODO Add constructor that accepts a java.util.Map of property values.
		// TODO Make getter/setter methods use values of this Map.
		// TODO Add validation constraints on the property (getter methods) and not the field.

		validatorFactory = configuration
				.addMapping(constraintMapping)
				.constraintValidatorFactory(null)
				.buildValidatorFactory();
		validator = validatorFactory.getValidator();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		if (validatorFactory != null) {
			validatorFactory.close();
		}
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void validateGeneratedClass() throws Exception {
		// TODO Use constructor that accepts a java.util.Map of property values.

		Object o = generatedClazz.newInstance();
		generatedClazz.getMethod("setBar", String.class).invoke(o, "Hello world");

		Set<ConstraintViolation<Object>> violations = validator.validate(o);
		assertEquals(1, violations.size());
		assertEquals("may not be null",
				violations.iterator().next().getMessage());

		generatedClazz.getMethod("setFoo", Integer.class).invoke(o, 1);
		violations = validator.validate(o);
		assertEquals(1, violations.size());
		assertEquals("must be greater than or equal to 2",
				violations.iterator().next().getMessage());

		generatedClazz.getMethod("setFoo", Integer.class).invoke(o, 10);
		violations = validator.validate(o);
		assertEquals(0, violations.size());
	}

}
