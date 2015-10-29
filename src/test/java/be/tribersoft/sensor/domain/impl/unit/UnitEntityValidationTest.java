package be.tribersoft.sensor.domain.impl.unit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;

public class UnitEntityValidationTest {

	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void failsWhenNameIsNull() {
		UnitEntity unitEntity = new UnitEntity(null);

		Set<ConstraintViolation<UnitEntity>> constraintViolations = validator.validate(unitEntity);
		assertThat(constraintViolations.size()).isEqualTo(1);
		assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("unit.validation.name.null");
	}

	@Test
	public void failsWhenNameIsTooLong() {
		UnitEntity unitEntity = new UnitEntity(StringUtils.leftPad("a", 256));

		Set<ConstraintViolation<UnitEntity>> constraintViolations = validator.validate(unitEntity);
		assertThat(constraintViolations.size()).isEqualTo(1);
		assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("unit.validation.name.too.long");
	}

	@Test
	public void failsWhenSymbolIsTooLong() {
		UnitEntity unitEntity = new UnitEntity(StringUtils.leftPad("a", 255));
		unitEntity.setSymbol(StringUtils.leftPad("a", 129));

		Set<ConstraintViolation<UnitEntity>> constraintViolations = validator.validate(unitEntity);
		assertThat(constraintViolations.size()).isEqualTo(1);
		assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("unit.validation.symbol.too.long");
	}

}
