package be.tribersoft.sensor.domain.impl.reading;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.impl.reading.ReadingEntity;
import be.tribersoft.sensor.domain.impl.sensor.SensorEntity;

@RunWith(MockitoJUnitRunner.class)
public class ReadingEntityValidationTest {

	private static final BigDecimal VALUE = BigDecimal.valueOf(123.4);

	private static Validator validator;

	@Mock
	private SensorEntity sensor;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void failsWhenValueIsNull() {
		ReadingEntity readingEntity = new ReadingEntity(null, sensor);

		Set<ConstraintViolation<ReadingEntity>> constraintViolations = validator.validate(readingEntity);
		assertThat(constraintViolations.size()).isEqualTo(1);
		assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("reading.validation.value.null");
	}

	@Test
	public void failsWhenSensorIsNull() {
		ReadingEntity readingEntity = new ReadingEntity(VALUE, null);

		Set<ConstraintViolation<ReadingEntity>> constraintViolations = validator.validate(readingEntity);
		assertThat(constraintViolations.size()).isEqualTo(1);
		assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("reading.validation.sensor.null");
	}

}
