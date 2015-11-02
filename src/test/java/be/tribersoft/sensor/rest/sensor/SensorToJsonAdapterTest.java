package be.tribersoft.sensor.rest.sensor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.sensor.Sensor;

@RunWith(MockitoJUnitRunner.class)
public class SensorToJsonAdapterTest {

	private static final Optional<String> DESCRIPTION = Optional.of("description");
	private static final String NAME = "name";
	private static final long VERSION = 2L;
	private static final String ID = "id";
	@InjectMocks
	private SensorToJsonAdapter sensorToJsonAdapter;
	@Mock
	private Sensor sensor;

	@Before
	public void setUp() {
		when(sensor.getId()).thenReturn(ID);
		when(sensor.getVersion()).thenReturn(VERSION);
		when(sensor.getName()).thenReturn(NAME);
		when(sensor.getDescription()).thenReturn(DESCRIPTION);
	}

	@Test
	public void delegatesToUnit() {
		assertThat(sensorToJsonAdapter.getId()).isEqualTo(ID);
		assertThat(sensorToJsonAdapter.getVersion()).isEqualTo(VERSION);
		assertThat(sensorToJsonAdapter.getName()).isEqualTo(NAME);
		assertThat(sensorToJsonAdapter.getDescription()).isEqualTo(DESCRIPTION);
	}
}
