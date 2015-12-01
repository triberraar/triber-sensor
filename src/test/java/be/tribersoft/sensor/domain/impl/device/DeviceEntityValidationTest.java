package be.tribersoft.sensor.domain.impl.device;

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

public class DeviceEntityValidationTest {

	private static final String NAME = "name";
	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void failsWhenNameIsNull() {
		DeviceEntity deviceEntity = new DeviceEntity(null);

		Set<ConstraintViolation<DeviceEntity>> constraintViolations = validator.validate(deviceEntity);
		assertThat(constraintViolations.size()).isEqualTo(1);
		assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("device.validation.name.null");
	}

	@Test
	public void failsWhenNameIsTooLong() {
		DeviceEntity deviceEntity = new DeviceEntity(StringUtils.leftPad("a", 257));

		Set<ConstraintViolation<DeviceEntity>> constraintViolations = validator.validate(deviceEntity);
		assertThat(constraintViolations.size()).isEqualTo(1);
		assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("device.validation.name.too.long");
	}

	@Test
	public void failsWhenDescriptionIsTooLong() {
		DeviceEntity deviceEntity = new DeviceEntity(NAME);
		deviceEntity.setDescription(Optional.of(StringUtils.leftPad("a", 4097)));

		Set<ConstraintViolation<DeviceEntity>> constraintViolations = validator.validate(deviceEntity);
		assertThat(constraintViolations.size()).isEqualTo(1);
		assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("device.validation.description.too.long");
	}

	@Test
	public void failsWhenLocationIsTooLong() {
		DeviceEntity deviceEntity = new DeviceEntity(NAME);
		deviceEntity.setLocation(Optional.of(StringUtils.leftPad("a", 4097)));

		Set<ConstraintViolation<DeviceEntity>> constraintViolations = validator.validate(deviceEntity);
		assertThat(constraintViolations.size()).isEqualTo(1);
		assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("device.validation.location.too.long");
	}
}
