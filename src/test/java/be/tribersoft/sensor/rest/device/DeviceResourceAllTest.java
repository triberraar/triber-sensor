package be.tribersoft.sensor.rest.device;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;

import be.tribersoft.sensor.domain.api.device.Device;
import be.tribersoft.sensor.domain.api.device.DeviceRepository;
import be.tribersoft.sensor.rest.VersionValidator;

@RunWith(MockitoJUnitRunner.class)
public class DeviceResourceAllTest {

	private static final String API_VERSION = "apiVersion";
	@InjectMocks
	private DeviceResource deviceResource;
	@Mock
	private DeviceRepository deviceRepository;
	@Mock
	private Device device1, device2;
	@Mock
	private DeviceHateoasBuilder deviceHateosBuilder;
	@Mock
	private Resources<Resource<DeviceToJsonAdapter>> resources;
	@Mock
	private VersionValidator versionValidator;

	@Before
	public void setUp() {
		doReturn(Arrays.<Device> asList(device1, device2)).when(deviceRepository).all();
		when(deviceHateosBuilder.build(Arrays.asList(device1, device2))).thenReturn(resources);
	}

	@Test
	public void delegatesToService() {
		Resources<Resource<DeviceToJsonAdapter>> deviceResources = deviceResource.all(API_VERSION);

		assertThat(deviceResources).isSameAs(resources);
		verify(versionValidator).validate(API_VERSION);
	}

}
