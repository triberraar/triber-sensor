package be.tribersoft.sensor.domain.impl.type;

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
public class TypeRepositoryImplAllTest {

	@InjectMocks
	private TypeRepositoryImpl typeRepositoryImpl;

	@Mock
	private TypeJpaRepository typeJpaRepository;
	@Mock
	private TypeEntity typeEntity1, typeEntity2;

	@Before
	public void setUp() {
		when(typeJpaRepository.findAll()).thenReturn(Arrays.asList(typeEntity1, typeEntity2));
	}

	@Test
	public void delegatesToSpringDataRepository() {
		List<TypeEntity> all = typeRepositoryImpl.all();

		verify(typeJpaRepository).findAll();
		assertThat(all).isEqualTo(Arrays.asList(typeEntity1, typeEntity2));
	}

}
