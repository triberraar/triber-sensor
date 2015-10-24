package be.tribersoft.sensor.domain.impl.type;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.type.TypeCreate;

@RunWith(MockitoJUnitRunner.class)
public class TypeFactoryTest {

	private static final String NAME = "name";

	private TypeFactory typeFactory = new TypeFactory();

	@Mock
	private TypeCreate typeCreate;

	@Before
	public void setUp() {
		when(typeCreate.getName()).thenReturn(NAME);
	}

	@Test
	public void createsATypeEntity() {
		TypeEntity createdTypeEntity = typeFactory.create(typeCreate);

		assertThat(createdTypeEntity).isNotNull();
		assertThat(createdTypeEntity.getName()).isEqualTo(NAME);
	}
}
