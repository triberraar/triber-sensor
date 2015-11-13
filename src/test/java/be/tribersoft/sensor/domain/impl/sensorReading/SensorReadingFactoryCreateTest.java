package be.tribersoft.sensor.domain.impl.sensorReading;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.common.DateFactory;
import be.tribersoft.sensor.domain.api.sensorReading.SensorReadingMessage;
import be.tribersoft.sensor.domain.impl.sensor.SensorEntity;
import be.tribersoft.sensor.domain.impl.sensor.SensorRepositoryImpl;

@RunWith(MockitoJUnitRunner.class)
public class SensorReadingFactoryCreateTest {

	private static final LocalDateTime DATE = LocalDateTime.now();
	private static final BigDecimal VALUE = BigDecimal.valueOf(123.4);
	private static final String SENSOR_ID = "sensor id";

	@InjectMocks
	private SensorReadingFactory sensorReadingFactory;

	@Mock
	private SensorRepositoryImpl sensorRepository;

	@Mock
	private SensorEntity sensor;

	@Mock
	private SensorReadingMessage sensorReadingMessage;

	@Before
	public void setUp() {
		DateFactory.fixateDate(DATE);
		when(sensorRepository.getById(SENSOR_ID)).thenReturn(sensor);
		when(sensorReadingMessage.getValue()).thenReturn(VALUE);
	}

	@Test
	public void createsASensorReading() {
		SensorReadingEntity createdSensorReading = sensorReadingFactory.create(SENSOR_ID, sensorReadingMessage);

		assertThat(createdSensorReading.getValue()).isEqualTo(VALUE);
		assertThat(createdSensorReading.getSensor()).isEqualTo(sensor);
		assertThat(createdSensorReading.getCreationDate()).isEqualTo(Date.from(DATE.atZone(ZoneId.systemDefault()).toInstant()));
	}
}
