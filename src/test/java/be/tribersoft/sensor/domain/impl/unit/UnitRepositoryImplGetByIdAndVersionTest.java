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

import be.tribersoft.sensor.domain.api.exception.ConcurrentModificationException;
import be.tribersoft.sensor.domain.api.unit.exception.UnitNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class UnitRepositoryImplGetByIdAndVersionTest {

	private static final long DIFFERENT_VERSION = 3L;

	private static final long VERSION = 2l;

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
		when(unitEntity.getVersion()).thenReturn(VERSION);
	}

	@Test(expected = UnitNotFoundException.class)
	public void failsWhenNoEntityWithId() {
		when(unitJpaRepository.findById(ID)).thenReturn(Optional.<UnitEntity> empty());

		unitRepositoryImpl.getByIdAndVersion(ID, VERSION);
	}

	@Test(expected = ConcurrentModificationException.class)
	public void failsWhenEntityWithIdHasDifferentVersion() {
		when(unitEntity.getVersion()).thenReturn(DIFFERENT_VERSION);

		unitRepositoryImpl.getByIdAndVersion(ID, VERSION);
	}

	@Test
	public void returnsEntityWithIdAndVersion() {
		UnitEntity foundUnitEntity = unitRepositoryImpl.getByIdAndVersion(ID, VERSION);

		assertThat(foundUnitEntity).isEqualTo(unitEntity);

	}

}
