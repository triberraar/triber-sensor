package be.tribersoft.sensor.rest.sensor;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

public class SensorUpdateJsonGetDescriptionTest {

	private static final String DESCRIPTION_VALUE = "description value";
	private SensorUpdateJson sensorUpdateJson = new SensorUpdateJson();

	@Test
	public void returnsEmptyWhenDescriptionIsNull() {
		Whitebox.setInternalState(sensorUpdateJson, "description", null);

		assertThat(sensorUpdateJson.getDescription().isPresent()).isFalse();
	}

	@Test
	public void returnsSymbolWhenDescriptionIsNotEmpty() {
		Whitebox.setInternalState(sensorUpdateJson, "description", Optional.of(DESCRIPTION_VALUE));

		assertThat(sensorUpdateJson.getDescription().get()).isEqualTo(DESCRIPTION_VALUE);

	}

}
