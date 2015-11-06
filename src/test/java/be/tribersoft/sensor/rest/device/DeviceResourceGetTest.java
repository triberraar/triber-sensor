package be.tribersoft.sensor.rest.device;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.hateoas.Resource;

import be.tribersoft.sensor.domain.api.device.Device;
import be.tribersoft.sensor.domain.api.device.DeviceRepository;

@RunWith(MockitoJUnitRunner.class)
public class DeviceResourceGetTest {

	private static String ID = "id";

	@InjectMocks
	private DeviceResource deviceResource;
	@Mock
	private DeviceRepository deviceRepository;
	@Mock
	private Device device;
	@Mock
	private DeviceHateoasBuilder deviceHateosBuilder;
	@Mock
	private Resource<DeviceToJsonAdapter> resource;

	@Before
	public void setUp() {
		when(deviceRepository.getById(ID)).thenReturn(device);
		when(deviceHateosBuilder.build(device)).thenReturn(resource);
	}

	@Test
	public void delegatesToService() {
		Resource<DeviceToJsonAdapter> returnedResource = deviceResource.get(ID);

		assertThat(returnedResource).isSameAs(resource);
	}

}
