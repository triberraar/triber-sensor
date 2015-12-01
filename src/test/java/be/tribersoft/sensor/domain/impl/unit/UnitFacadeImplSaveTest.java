package be.tribersoft.sensor.domain.impl.unit;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.unit.UnitMessage;

@RunWith(MockitoJUnitRunner.class)
public class UnitFacadeImplSaveTest {
	@Mock
	private UnitRepositoryImpl unitRepository;
	@Mock
	private UnitFactory unitFactory;
	@Mock
	private UnitMessage unitMessage;
	@Mock
	private UnitEntity unitEntity;
	@InjectMocks
	private UnitFacadeImpl unitFacadeImpl;

	@Before
	public void setUp() {
		when(unitFactory.create(unitMessage)).thenReturn(unitEntity);
	}

	@Test
	public void savesCreatedUnit() {
		unitFacadeImpl.save(unitMessage);

		verify(unitRepository).save(unitEntity);
	}

}
