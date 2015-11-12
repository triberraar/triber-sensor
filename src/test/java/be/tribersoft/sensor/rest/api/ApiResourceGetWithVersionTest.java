package be.tribersoft.sensor.rest.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.hateoas.Resource;

import be.tribersoft.common.rest.IncorrectApiVersionException;

@RunWith(MockitoJUnitRunner.class)
public class ApiResourceGetWithVersionTest {

	private static final String DIFFERENT_VERSION = "different version";

	private static final String API_VERSION = "apiVersion";

	@InjectMocks
	private ApiResource apiResource;

	@Mock
	private ApiHateoasBuilder apiHateoasBuilder;

	@Mock
	private Resource<ApiToJsonAdapter> apiToJsoAdapterResource;

	@Before
	public void setUp() {
		Whitebox.setInternalState(apiResource, API_VERSION, API_VERSION);
		when(apiHateoasBuilder.build(API_VERSION)).thenReturn(apiToJsoAdapterResource);
	}

	@Test(expected = IncorrectApiVersionException.class)
	public void failsWhenVersionIsDifferent() {
		apiResource.getWithVersion(DIFFERENT_VERSION);
	}

	@Test
	public void returnsApi() {
		Resource<ApiToJsonAdapter> resource = apiResource.getWithVersion(API_VERSION);

		assertThat(resource).isSameAs(apiToJsoAdapterResource);
	}
}
