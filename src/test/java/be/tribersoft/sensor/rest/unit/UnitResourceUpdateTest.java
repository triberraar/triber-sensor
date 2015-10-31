package be.tribersoft.sensor.rest.unit;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.service.api.unit.UnitService;

@RunWith(MockitoJUnitRunner.class)
public class UnitResourceUpdateTest {
	private static final long VERSION = 2L;
	private static final String ID = "id";
	@InjectMocks
	private UnitResource unitResource;
	@Mock
	private UnitService unitService;
	@Mock
	private UnitUpdateJson unitUpdateJson;

	@Before
	public void setUp() {
		when(unitUpdateJson.getVersion()).thenReturn(VERSION);
	}

	@Test
	public void delegatesToService() {
		unitResource.update(ID, unitUpdateJson);

		verify(unitService).update(ID, VERSION, unitUpdateJson);
	}
}
