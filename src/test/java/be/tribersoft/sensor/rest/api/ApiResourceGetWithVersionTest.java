package be.tribersoft.sensor.rest.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.hateoas.Resource;

import be.tribersoft.sensor.rest.VersionValidator;

@RunWith(MockitoJUnitRunner.class)
public class ApiResourceGetWithVersionTest {

	private static final String API_VERSION = "api version";

	@InjectMocks
	private ApiResource apiResource;

	@Mock
	private ApiHateoasBuilder apiHateoasBuilder;

	@Mock
	private Resource<ApiToJsonAdapter> apiToJsoAdapterResource;

	@Mock
	private VersionValidator versionValidator;

	@Before
	public void setUp() {
		when(apiHateoasBuilder.build()).thenReturn(apiToJsoAdapterResource);
	}

	@Test
	public void returnsApi() {
		Resource<ApiToJsonAdapter> resource = apiResource.getWithVersion(API_VERSION);

		verify(versionValidator).validate(API_VERSION);
		assertThat(resource).isSameAs(apiToJsoAdapterResource);
	}
}
