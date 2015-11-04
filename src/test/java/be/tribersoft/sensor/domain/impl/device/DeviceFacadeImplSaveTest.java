package be.tribersoft.sensor.domain.impl.device;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.type.DeviceMessage;

@RunWith(MockitoJUnitRunner.class)
public class DeviceFacadeImplSaveTest {

	@InjectMocks
	private DeviceFacadeImpl deviceFacade;

	@Mock
	private DeviceFactory deviceFactory;
	@Mock
	private DeviceRepositoryImpl deviceRepositoryImpl;
	@Mock
	private DeviceMessage deviceMessage;
	@Mock
	private DeviceEntity device;

	@Before
	public void setUp() {
		when(deviceFactory.create(deviceMessage)).thenReturn(device);
	}

	@Test
	public void savesCreatedDevice() {
		deviceFacade.save(deviceMessage);

		verify(deviceRepositoryImpl).save(device);
	}

}
