package be.tribersoft.sensor.domain.impl.type;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.type.exception.TypeNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class TypeRepositoryImplGetByIdTest {

	private static final String ID = "id";

	@InjectMocks
	private TypeRepositoryImpl typeRepositoryImpl;

	@Mock
	private TypeJpaRepository typeJpaRepository;
	@Mock
	private TypeEntity typeEntity;

	@Before
	public void setUp() {
		when(typeJpaRepository.findById(ID)).thenReturn(Optional.of(typeEntity));
	}

	@Test(expected = TypeNotFoundException.class)
	public void failsWhenNoEntityWithId() {
		when(typeJpaRepository.findById(ID)).thenReturn(Optional.<TypeEntity> empty());

		typeRepositoryImpl.getById(ID);
	}

	@Test
	public void returnsEntityWithIdAndVersion() {
		TypeEntity foundTypeEntity = typeRepositoryImpl.getById(ID);

		assertThat(foundTypeEntity).isEqualTo(typeEntity);

	}

}
