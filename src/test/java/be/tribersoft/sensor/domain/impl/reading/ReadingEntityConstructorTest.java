package be.tribersoft.sensor.domain.impl.reading;

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
import be.tribersoft.sensor.domain.impl.reading.ReadingEntity;
import be.tribersoft.sensor.domain.impl.sensor.SensorEntity;

@RunWith(MockitoJUnitRunner.class)
public class ReadingEntityConstructorTest {
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
		ReadingEntity reading = new ReadingEntity(VALUE, sensor);

		assertThat(reading.getValue()).isEqualTo(VALUE);
		assertThat(reading.getSensor()).isEqualTo(sensor);
		assertThat(reading.getCreationDate()).isEqualTo(Date.from(DATE.atZone(ZoneId.systemDefault()).toInstant()));
	}

}
