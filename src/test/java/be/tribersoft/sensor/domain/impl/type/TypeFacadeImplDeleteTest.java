package be.tribersoft.sensor.domain.impl.type;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.sensor.SensorRepository;
import be.tribersoft.sensor.domain.api.type.exception.TypeStillInUseException;

@RunWith(MockitoJUnitRunner.class)
public class TypeFacadeImplDeleteTest {
	private static final long VERSION = 2L;
	private static final String ID = "id";
	@Mock
	private TypeEntity typeEntity;
	@InjectMocks
	private TypeFacadeImpl typeFacadeImpl;
	@Mock
	private TypeRepositoryImpl TypeRepository;
	@Mock
	private SensorRepository sensorRepository;

	@Before
	public void setUp() {
		when(TypeRepository.getByIdAndVersion(ID, VERSION)).thenReturn(typeEntity);
	}

	@Test
	public void deletesTypeEntity() {
		typeFacadeImpl.delete(ID, VERSION);

		verify(TypeRepository).delete(typeEntity);
	}

	@Test(expected = TypeStillInUseException.class)
	public void failsWhenASensorIsStillUsingThisType() {
		when(sensorRepository.typeInUse(ID)).thenReturn(true);

		typeFacadeImpl.delete(ID, VERSION);
	}

}
