package be.tribersoft.sensor.domain.impl.unit;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.unit.UnitMessage;

@RunWith(MockitoJUnitRunner.class)
public class UnitUpdaterUpdateTest {

	private static final String SYMBOL = "symbol";
	private static final String NAME = "name";

	private UnitUpdater updater = new UnitUpdater();
	@Mock
	private UnitEntity unit;
	@Mock
	private UnitMessage unitMessage;

	@Before
	public void setup() {
		when(unitMessage.getName()).thenReturn(NAME);
		when(unitMessage.getSymbol()).thenReturn(SYMBOL);
	}

	@Test
	public void updatesUnit() {
		updater.update(unit, unitMessage);

		verify(unit).setName(NAME);
		verify(unit).setSymbol(SYMBOL);
	}
}
