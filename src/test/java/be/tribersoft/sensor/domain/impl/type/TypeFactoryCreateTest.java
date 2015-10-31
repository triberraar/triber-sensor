package be.tribersoft.sensor.domain.impl.type;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.type.TypeMessage;

@RunWith(MockitoJUnitRunner.class)
public class TypeFactoryCreateTest {

	private static final String NAME = "name";

	private TypeFactory typeFactory = new TypeFactory();

	@Mock
	private TypeMessage typeMessage;

	@Before
	public void setUp() {
		when(typeMessage.getName()).thenReturn(NAME);
	}

	@Test
	public void createsATypeEntity() {
		TypeEntity createdTypeEntity = typeFactory.create(typeMessage);

		assertThat(createdTypeEntity).isNotNull();
		assertThat(createdTypeEntity.getName()).isEqualTo(NAME);
	}
}
