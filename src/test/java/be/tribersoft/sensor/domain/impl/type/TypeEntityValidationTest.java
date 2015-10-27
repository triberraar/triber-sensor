package be.tribersoft.sensor.domain.impl.type;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;

public class TypeEntityValidationTest {

	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void failsWhenNameIsNull() {
		TypeEntity typeEntity = new TypeEntity(null);

		Set<ConstraintViolation<TypeEntity>> constraintViolations = validator.validate(typeEntity);
		assertThat(constraintViolations.size()).isEqualTo(1);
		assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("type.validation.name.null");
	}

	@Test
	public void failsWhenNameIsTooLong() {
		TypeEntity typeEntity = new TypeEntity(StringUtils.leftPad("a", 256));
		Set<ConstraintViolation<TypeEntity>> constraintViolations = validator.validate(typeEntity);
		assertThat(constraintViolations.size()).isEqualTo(1);
		assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("type.validation.name.too.long");
	}
}
