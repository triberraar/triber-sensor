package be.tribersoft.sensor.rest.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.hateoas.Resource;

@RunWith(MockitoJUnitRunner.class)
public class ApiResourceGetWithVersionTest {

	@InjectMocks
	private ApiResource apiResource;

	@Mock
	private ApiHateoasBuilder apiHateoasBuilder;

	@Mock
	private Resource<ApiToJsonAdapter> apiToJsoAdapterResource;

	@Before
	public void setUp() {
		when(apiHateoasBuilder.build()).thenReturn(apiToJsoAdapterResource);
	}

	@Test
	public void returnsApi() {
		Resource<ApiToJsonAdapter> resource = apiResource.get();

		assertThat(resource).isSameAs(apiToJsoAdapterResource);
	}
}
