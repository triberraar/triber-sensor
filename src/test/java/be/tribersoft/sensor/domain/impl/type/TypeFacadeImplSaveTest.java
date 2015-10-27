package be.tribersoft.sensor.domain.impl.type;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.type.TypeCreate;

@RunWith(MockitoJUnitRunner.class)
public class TypeFacadeImplSaveTest {
	@Mock
	private TypeRepositoryImpl typeRepository;
	@Mock
	private TypeFactory typeFactory;
	@Mock
	private TypeCreate typeCreate;
	@Mock
	private TypeEntity typeEntity;
	@InjectMocks
	private TypeFacadeImpl typeFacadeImpl;

	@Before
	public void setUp() {
		when(typeFactory.create(typeCreate)).thenReturn(typeEntity);
	}

	@Test
	public void savesCreatedType() {
		typeFacadeImpl.save(typeCreate);

		verify(typeRepository).save(typeEntity);
	}

}
