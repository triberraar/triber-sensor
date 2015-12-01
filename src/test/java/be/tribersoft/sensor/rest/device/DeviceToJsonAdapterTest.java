package be.tribersoft.sensor.rest.device;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.device.Device;

@RunWith(MockitoJUnitRunner.class)
public class DeviceToJsonAdapterTest {

	private static final Optional<String> DESCRIPTION = Optional.of("description");
	private static final String NAME = "name";
	private static final long VERSION = 2L;
	private static final String ID = "id";
	@InjectMocks
	private DeviceToJsonAdapter deviceToJsonAdapter;
	@Mock
	private Device device;

	@Before
	public void setUp() {
		when(device.getId()).thenReturn(ID);
		when(device.getVersion()).thenReturn(VERSION);
		when(device.getName()).thenReturn(NAME);
		when(device.getDescription()).thenReturn(DESCRIPTION);
	}

	@Test
	public void delegatesToUnit() {
		assertThat(deviceToJsonAdapter.getId()).isEqualTo(ID);
		assertThat(deviceToJsonAdapter.getVersion()).isEqualTo(VERSION);
		assertThat(deviceToJsonAdapter.getName()).isEqualTo(NAME);
		assertThat(deviceToJsonAdapter.getDescription()).isEqualTo(DESCRIPTION);
	}
}
