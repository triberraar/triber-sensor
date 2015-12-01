package be.tribersoft.sensor.rest.unit;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.rest.VersionValidator;
import be.tribersoft.sensor.service.api.unit.UnitService;

@RunWith(MockitoJUnitRunner.class)
public class UnitResourceUpdateTest {
	private static final String API_VERSION = "apiVersion";
	private static final long VERSION = 2L;
	private static final String ID = "id";
	@InjectMocks
	private UnitResource unitResource;
	@Mock
	private UnitService unitService;
	@Mock
	private UnitUpdateJson unitUpdateJson;
	@Mock
	private VersionValidator versionValidator;

	@Before
	public void setUp() {
		when(unitUpdateJson.getVersion()).thenReturn(VERSION);
	}

	@Test
	public void delegatesToService() {
		unitResource.update(API_VERSION, ID, unitUpdateJson);

		verify(unitService).update(ID, VERSION, unitUpdateJson);
		verify(versionValidator).validate(API_VERSION);
	}
}
