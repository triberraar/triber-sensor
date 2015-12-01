package be.tribersoft.sensor.service.impl.unit;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.unit.UnitFacade;
import be.tribersoft.sensor.domain.api.unit.UnitMessage;

@RunWith(MockitoJUnitRunner.class)
public class UnitServiceImplUpdateTest {

	private static final long VERSION = 2L;
	private static final String ID = "id";
	@InjectMocks
	private UnitServiceImpl unitService;
	@Mock
	private UnitFacade unitFacade;
	@Mock
	private UnitMessage unitMessage;

	@Test
	public void delegatesToFacade() {
		unitService.update(ID, VERSION, unitMessage);

		verify(unitFacade).update(ID, VERSION, unitMessage);
	}

}
