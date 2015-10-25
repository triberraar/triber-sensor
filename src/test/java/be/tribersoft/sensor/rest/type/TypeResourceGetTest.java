package be.tribersoft.sensor.rest.type;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.hateoas.Resource;

import be.tribersoft.sensor.domain.api.type.Type;
import be.tribersoft.sensor.domain.api.type.TypeRepository;

@RunWith(MockitoJUnitRunner.class)
public class TypeResourceGetTest {

	private static String ID = "id";

	@InjectMocks
	private TypeResource typeResource;
	@Mock
	private TypeRepository typeRepository;
	@Mock
	private Type type;
	@Mock
	private TypeHateoasBuilder typeHateosBuilder;
	@Mock
	private Resource<TypeToJsonAdapter> resource;

	@Before
	public void setUp() {
		when(typeRepository.getById(ID)).thenReturn(type);
		when(typeHateosBuilder.build(type)).thenReturn(resource);
	}

	@Test
	public void delegatesToService() {
		Resource<TypeToJsonAdapter> returnedResource = typeResource.get(ID);

		assertThat(returnedResource).isSameAs(resource);
	}

}
