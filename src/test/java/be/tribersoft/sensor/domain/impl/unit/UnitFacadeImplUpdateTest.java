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
public class UnitFacadeImplUpdateTest {
	private static final long VERSION = 2L;
	private static final String ID = "id";
	@Mock
	private UnitUpdater unitUpdater;
	@Mock
	private UnitEntity unitEntity;
	@InjectMocks
	private UnitFacadeImpl unitFacadeImpl;
	@Mock
	private UnitMessage unitMessage;
	@Mock
	private UnitRepositoryImpl UnitRepository;

	@Before
	public void setUp() {
		when(UnitRepository.getByIdAndVersion(ID, VERSION)).thenReturn(unitEntity);
	}

	@Test
	public void updatesUnitEntity() {
		unitFacadeImpl.update(ID, VERSION, unitMessage);

		verify(unitUpdater).update(unitEntity, unitMessage);
	}

}
