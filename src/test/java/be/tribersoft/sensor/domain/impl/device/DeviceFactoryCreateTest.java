package be.tribersoft.sensor.domain.impl.device;

import static org.assertj.core.api.Assertions.assertThat;
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
public class DeviceFactoryCreateTest {

	private static final Optional<String> DESCRIPTION = Optional.of("description");
	private static final Optional<String> LOCATION = Optional.of("location");
	private static final String NAME = "name";

	@InjectMocks
	private DeviceFactory deviceFactory;

	@Mock
	private DeviceMessage deviceMessage;

	@Before
	public void setUp() {
		when(deviceMessage.getName()).thenReturn(NAME);
		when(deviceMessage.getDescription()).thenReturn(DESCRIPTION);
		when(deviceMessage.getLocation()).thenReturn(LOCATION);
	}

	@Test
	public void createsADevice() {
		DeviceEntity deviceEntity = deviceFactory.create(deviceMessage);

		assertThat(deviceEntity.getName()).isEqualTo(NAME);
		assertThat(deviceEntity.getDescription()).isEqualTo(DESCRIPTION);
		assertThat(deviceEntity.getLocation()).isEqualTo(LOCATION);
	}
}
