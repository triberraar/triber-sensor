package be.tribersoft.sensor.domain.impl.sensorReading;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.common.DateFactory;
import be.tribersoft.sensor.domain.impl.sensor.SensorEntity;

@RunWith(MockitoJUnitRunner.class)
public class SensorReadingEntityConstructorTest {
	private static final LocalDateTime DATE = LocalDateTime.now();
	private static final BigDecimal VALUE = BigDecimal.valueOf(123.3);

	@Mock
	private SensorEntity sensor;

	@Before
	public void setUp() {
		DateFactory.fixateDate(DATE);
	}

	@Test
	public void constructsSuccessfully() {
		SensorReadingEntity sensorReading = new SensorReadingEntity(VALUE, sensor);

		assertThat(sensorReading.getValue()).isEqualTo(VALUE);
		assertThat(sensorReading.getSensor()).isEqualTo(sensor);
		assertThat(sensorReading.getCreationDate()).isEqualTo(Date.from(DATE.atZone(ZoneId.systemDefault()).toInstant()));
	}

}
