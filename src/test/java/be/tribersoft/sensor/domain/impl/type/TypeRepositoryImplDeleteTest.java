package be.tribersoft.sensor.domain.impl.type;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TypeRepositoryImplDeleteTest {

	@InjectMocks
	private TypeRepositoryImpl typeRepositoryImpl;

	@Mock
	private TypeJpaRepository typeJpaRepository;
	@Mock
	private TypeEntity typeEntity;

	@Test
	public void delegatesToSpringDataRepository() {
		typeRepositoryImpl.delete(typeEntity);

		verify(typeJpaRepository).delete(typeEntity);
	}

}
