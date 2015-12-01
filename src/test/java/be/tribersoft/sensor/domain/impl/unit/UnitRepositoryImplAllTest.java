package be.tribersoft.sensor.domain.impl.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UnitRepositoryImplAllTest {

	@InjectMocks
	private UnitRepositoryImpl unitRepositoryImpl;

	@Mock
	private UnitJpaRepository unitJpaRepository;
	@Mock
	private UnitEntity unitEntity1, unitEntity2;

	@Before
	public void setUp() {
		when(unitJpaRepository.findAllByOrderByCreationDateDesc()).thenReturn(Arrays.asList(unitEntity1, unitEntity2));
	}

	@Test
	public void delegatesToSpringDataRepository() {
		List<UnitEntity> all = unitRepositoryImpl.all();

		verify(unitJpaRepository).findAllByOrderByCreationDateDesc();
		assertThat(all).isEqualTo(Arrays.asList(unitEntity1, unitEntity2));
	}

}
