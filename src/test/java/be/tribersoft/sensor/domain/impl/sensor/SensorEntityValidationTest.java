package be.tribersoft.sensor.domain.impl.sensor;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.impl.type.TypeEntity;
import be.tribersoft.sensor.domain.impl.unit.UnitEntity;

@RunWith(MockitoJUnitRunner.class)
public class SensorEntityValidationTest {

	private static final String NAME = "name";

	private static Validator validator;

	@Mock
	private UnitEntity unit;
	@Mock
	private TypeEntity type;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void failsWhenNameIsNull() {
		SensorEntity sensorEntity = new SensorEntity(null, type, unit);

		Set<ConstraintViolation<SensorEntity>> constraintViolations = validator.validate(sensorEntity);
		assertThat(constraintViolations.size()).isEqualTo(1);
		assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("sensor.validation.name.null");
	}

	@Test
	public void failsWhenNameIsTooLong() {
		SensorEntity sensorEntity = new SensorEntity(StringUtils.leftPad("a", 257), type, unit);

		Set<ConstraintViolation<SensorEntity>> constraintViolations = validator.validate(sensorEntity);
		assertThat(constraintViolations.size()).isEqualTo(1);
		assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("sensor.validation.name.too.long");
	}

	@Test
	public void failsWhenDescriptionIsTooLong() {
		SensorEntity sensorEntity = new SensorEntity(NAME, type, unit);
		sensorEntity.setDescription(Optional.of(StringUtils.leftPad("q", 4097)));

		Set<ConstraintViolation<SensorEntity>> constraintViolations = validator.validate(sensorEntity);
		assertThat(constraintViolations.size()).isEqualTo(1);
		assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("sensor.validation.description.too.long");
	}

	@Test
	public void failsWhenTypeIsNull() {
		SensorEntity sensorEntity = new SensorEntity(NAME, null, unit);

		Set<ConstraintViolation<SensorEntity>> constraintViolations = validator.validate(sensorEntity);
		assertThat(constraintViolations.size()).isEqualTo(1);
		assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("sensor.validation.type.null");
	}

	@Test
	public void failsWhenUnitIsNull() {
		SensorEntity sensorEntity = new SensorEntity(NAME, type, null);

		Set<ConstraintViolation<SensorEntity>> constraintViolations = validator.validate(sensorEntity);
		assertThat(constraintViolations.size()).isEqualTo(1);
		assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("sensor.validation.unit.null");
	}

}
