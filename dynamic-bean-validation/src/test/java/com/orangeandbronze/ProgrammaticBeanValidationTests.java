package com.orangeandbronze;

import static org.junit.Assert.*;

import java.lang.annotation.ElementType;
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
 * Tests to show how to programmatically add Bean Validation constraints at
 * runtime. This means that the bean being validated <em>does not</em> have any
 * Bean Validation constraints at compile-time (see {@link Car} class).
 * <p>
 * The constraints are added programmatically at runtime and tested to see if
 * constraints are indeed being applied.
 *
 * @see DynamicBeanValidationTests
 */
public class ProgrammaticBeanValidationTests {

	private static ValidatorFactory validatorFactory;
	private static Validator validator;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		HibernateValidatorConfiguration configuration;
		configuration = Validation
				.byProvider(HibernateValidator.class)
				.configure();
		ConstraintMapping constraintMapping = configuration.createConstraintMapping();

		// Yey! We can define constraints at runtime!
		constraintMapping
			.type(Car.class)
				// @NotNull
				.property("manufacturer", ElementType.FIELD)
					.constraint(new NotNullDef())
				// @NotNull @Size(min=2, max=14)
				.property("licensePlate", ElementType.FIELD)
					.constraint(new NotNullDef())
					.constraint(new SizeDef().min(2).max(14))
				// @NotNull @Min(2)
				.property("seatCount", ElementType.FIELD)
					.constraint(new MinDef().value(2));

		// NOTE: Use GenericConstraintDef<> for custom constraints

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
	public void manufacturerCannotBeNull() {
		Car car = new Car(null, "ABC1234", 2);
		Set<ConstraintViolation<Car>> violations = validator.validate(car);
		assertEquals(1, violations.size());
		assertEquals("may not be null",
				violations.iterator().next().getMessage());
	}

	@Test
	public void licensePlateTooShort() {
		Car car = new Car("BMW", "D", 4);
		Set<ConstraintViolation<Car>> violations = validator.validate(car);
		assertEquals(1, violations.size());
		assertEquals("size must be between 2 and 14",
				violations.iterator().next().getMessage());
	}

	@Test
	public void licensePlateTooLong() {
		Car car = new Car("BMW", "ABCD-1234567890", 4);
		Set<ConstraintViolation<Car>> violations = validator.validate(car);
		assertEquals(1, violations.size());
		assertEquals("size must be between 2 and 14",
				violations.iterator().next().getMessage());
	}

	@Test
	public void seatCountTooLow() {
		Car car = new Car("Toyota", "ABC123", 1);
		Set<ConstraintViolation<Car>> violations = validator.validate(car);
		assertEquals(1, violations.size());
		assertEquals("must be greater than or equal to 2", 
				violations.iterator().next().getMessage());
	}

	@Test
	public void validCar() throws Exception {
		Car car = new Car("Honda", "ABC123", 4);
		Set<ConstraintViolation<Car>> violations = validator.validate(car);
		assertEquals(0, violations.size());
	}

}
