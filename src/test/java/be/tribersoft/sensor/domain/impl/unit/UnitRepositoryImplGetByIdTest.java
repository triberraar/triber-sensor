package be.tribersoft.sensor.domain.impl.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.unit.exception.UnitNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class UnitRepositoryImplGetByIdTest {

	private static final String ID = "id";

	@InjectMocks
	private UnitRepositoryImpl unitRepositoryImpl;

	@Mock
	private UnitJpaRepository unitJpaRepository;
	@Mock
	private UnitEntity unitEntity;

	@Before
	public void setUp() {
		when(unitJpaRepository.findById(ID)).thenReturn(Optional.of(unitEntity));
	}

	@Test(expected = UnitNotFoundException.class)
	public void failsWhenNoEntityWithId() {
		when(unitJpaRepository.findById(ID)).thenReturn(Optional.<UnitEntity> empty());

		unitRepositoryImpl.getById(ID);
	}

	@Test
	public void returnsEntityWithIdAndVersion() {
		UnitEntity foundUnitEntity = unitRepositoryImpl.getById(ID);

		assertThat(foundUnitEntity).isEqualTo(unitEntity);

	}

}
