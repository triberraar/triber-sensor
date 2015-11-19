package be.tribersoft.sensor.rest.type;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;

import be.tribersoft.sensor.domain.api.type.Type;
import be.tribersoft.sensor.domain.api.type.TypeRepository;
import be.tribersoft.sensor.rest.VersionValidator;

@RunWith(MockitoJUnitRunner.class)
public class TypeResourceAllTest {

	private static final String API_VERSION = "version";
	@InjectMocks
	private TypeResource typeResource;
	@Mock
	private TypeRepository typeRepository;
	@Mock
	private Type type1, type2;
	@Mock
	private TypeHateoasBuilder typeHateosBuilder;
	@Mock
	private Resources<Resource<TypeToJsonAdapter>> resources;
	@Mock
	private VersionValidator versionValidator;

	@Before
	public void setUp() {
		doReturn(Arrays.<Type> asList(type1, type2)).when(typeRepository).all();
		when(typeHateosBuilder.build(Arrays.asList(type1, type2))).thenReturn(resources);
	}

	@Test
	public void delegatesToService() {
		Resources<Resource<TypeToJsonAdapter>> typeResources = typeResource.all(API_VERSION);

		assertThat(typeResources).isSameAs(resources);
		verify(versionValidator).validate(API_VERSION);
	}

}
