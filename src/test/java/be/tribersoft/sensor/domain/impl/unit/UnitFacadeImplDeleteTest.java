package be.tribersoft.sensor.domain.impl.unit;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UnitFacadeImplDeleteTest {
	private static final long VERSION = 2L;
	private static final String ID = "id";
	@Mock
	private UnitEntity unitEntity;
	@InjectMocks
	private UnitFacadeImpl unitFacadeImpl;
	@Mock
	private UnitRepositoryImpl UnitRepository;

	@Before
	public void setUp() {
		when(UnitRepository.getByIdAndVersion(ID, VERSION)).thenReturn(unitEntity);
	}

	@Test
	public void deletesUnitEntity() {
		unitFacadeImpl.delete(ID, VERSION);

		verify(UnitRepository).delete(unitEntity);
	}

}
