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
public class UnitResourceDeleteTest {
	private static final String API_VERSION = "apiVersion";
	private static final long VERSION = 2L;
	private static final String ID = "id";
	@InjectMocks
	private UnitResource unitResource;
	@Mock
	private UnitService unitService;
	@Mock
	private UnitDeleteJson unitDeleteJson;
	@Mock
	private VersionValidator versionValidator;

	@Before
	public void setUp() {
		when(unitDeleteJson.getVersion()).thenReturn(VERSION);
	}

	@Test
	public void delegatesToService() {
		unitResource.delete(API_VERSION, ID, unitDeleteJson);

		verify(unitService).delete(ID, VERSION);
		verify(versionValidator).validate(API_VERSION);
	}
}
