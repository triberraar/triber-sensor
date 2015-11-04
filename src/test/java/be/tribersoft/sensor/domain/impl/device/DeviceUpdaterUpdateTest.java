package be.tribersoft.sensor.domain.impl.device;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.type.DeviceMessage;

@RunWith(MockitoJUnitRunner.class)
public class DeviceUpdaterUpdateTest {

	private static final String NAME = "name";
	private static final Optional<String> DESCRIPTION = Optional.of("description");
	private static final Optional<String> LOCATION = Optional.of("location");

	@InjectMocks
	private DeviceUpdater deviceUpdater;

	@Mock
	private DeviceEntity device;

	@Mock
	private DeviceMessage deviceMessage;

	@Before
	public void setUp() {
		when(deviceMessage.getDescription()).thenReturn(DESCRIPTION);
		when(deviceMessage.getLocation()).thenReturn(LOCATION);
		when(deviceMessage.getName()).thenReturn(NAME);
	}

	@Test
	public void createsADevice() {
		deviceUpdater.update(device, deviceMessage);

		verify(device).setName(NAME);
		verify(device).setDescription(DESCRIPTION);
		verify(device).setLocation(LOCATION);
	}
}
