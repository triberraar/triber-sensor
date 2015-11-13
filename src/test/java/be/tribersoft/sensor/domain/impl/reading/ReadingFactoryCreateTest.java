package be.tribersoft.sensor.domain.impl.reading;

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
import be.tribersoft.sensor.domain.api.reading.ReadingMessage;
import be.tribersoft.sensor.domain.impl.reading.ReadingEntity;
import be.tribersoft.sensor.domain.impl.reading.ReadingFactory;
import be.tribersoft.sensor.domain.impl.sensor.SensorEntity;
import be.tribersoft.sensor.domain.impl.sensor.SensorRepositoryImpl;

@RunWith(MockitoJUnitRunner.class)
public class ReadingFactoryCreateTest {

	private static final LocalDateTime DATE = LocalDateTime.now();
	private static final BigDecimal VALUE = BigDecimal.valueOf(123.4);
	private static final String SENSOR_ID = "sensor id";

	@InjectMocks
	private ReadingFactory readingFactory;

	@Mock
	private SensorRepositoryImpl sensorRepository;

	@Mock
	private SensorEntity sensor;

	@Mock
	private ReadingMessage readingMessage;

	@Before
	public void setUp() {
		DateFactory.fixateDate(DATE);
		when(sensorRepository.getById(SENSOR_ID)).thenReturn(sensor);
		when(readingMessage.getValue()).thenReturn(VALUE);
	}

	@Test
	public void createsAReading() {
		ReadingEntity createdReading = readingFactory.create(SENSOR_ID, readingMessage);

		assertThat(createdReading.getValue()).isEqualTo(VALUE);
		assertThat(createdReading.getSensor()).isEqualTo(sensor);
		assertThat(createdReading.getCreationDate()).isEqualTo(Date.from(DATE.atZone(ZoneId.systemDefault()).toInstant()));
	}
}
