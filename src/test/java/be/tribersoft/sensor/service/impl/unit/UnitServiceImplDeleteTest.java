package be.tribersoft.sensor.service.impl.unit;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.unit.UnitFacade;

@RunWith(MockitoJUnitRunner.class)
public class UnitServiceImplDeleteTest {

	private static final long VERSION = 2L;
	private static final String ID = "id";
	@InjectMocks
	private UnitServiceImpl unitService;
	@Mock
	private UnitFacade unitFacade;

	@Test
	public void delegatesToFacade() {
		unitService.delete(ID, VERSION);

		verify(unitFacade).delete(ID, VERSION);
	}

}
