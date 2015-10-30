package be.tribersoft.sensor.domain.impl.type;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.type.TypeMessage;

@RunWith(MockitoJUnitRunner.class)
public class TypeFacadeImplSaveTest {
	@Mock
	private TypeRepositoryImpl typeRepository;
	@Mock
	private TypeFactory typeFactory;
	@Mock
	private TypeMessage typeMessage;
	@Mock
	private TypeEntity typeEntity;
	@InjectMocks
	private TypeFacadeImpl typeFacadeImpl;

	@Before
	public void setUp() {
		when(typeFactory.create(typeMessage)).thenReturn(typeEntity);
	}

	@Test
	public void savesCreatedType() {
		typeFacadeImpl.save(typeMessage);

		verify(typeRepository).save(typeEntity);
	}

}
