package be.tribersoft.sensor.rest.unit;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.service.api.unit.UnitService;

@RunWith(MockitoJUnitRunner.class)
public class UnitResourceSaveTest {
	@InjectMocks
	private UnitResource unitResource;
	@Mock
	private UnitService unitService;
	@Mock
	private UnitPostJson unitPostJson;

	@Test
	public void delegatesToService() {
		unitResource.save(unitPostJson);

		verify(unitService).save(unitPostJson);
	}
}
