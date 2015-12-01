package be.tribersoft.sensor.domain.impl.unit;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UnitRepositoryImplSaveTest {

	@InjectMocks
	private UnitRepositoryImpl unitRepositoryImpl;

	@Mock
	private UnitJpaRepository unitJpaRepository;
	@Mock
	private UnitEntity unitEntity;

	@Test
	public void delegatesToSpringDataRepository() {
		unitRepositoryImpl.save(unitEntity);

		verify(unitJpaRepository).save(unitEntity);
	}

}
