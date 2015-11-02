package be.tribersoft.sensor.rest.sensor;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

public class SensorPostJsonGetDescriptionTest {

	private static final String DESCRIPTION_VALUE = "description value";
	private SensorPostJson sensorPostJson = new SensorPostJson();

	@Test
	public void returnsEmptyWhenDescriptionIsNull() {
		Whitebox.setInternalState(sensorPostJson, "description", null);

		assertThat(sensorPostJson.getDescription().isPresent()).isFalse();
	}

	@Test
	public void returnsSymbolWhenDescriptionIsNotEmpty() {
		Whitebox.setInternalState(sensorPostJson, "description", Optional.of(DESCRIPTION_VALUE));

		assertThat(sensorPostJson.getDescription().get()).isEqualTo(DESCRIPTION_VALUE);

	}

}
