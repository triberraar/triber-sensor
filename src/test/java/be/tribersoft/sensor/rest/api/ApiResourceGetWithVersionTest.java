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

import be.tribersoft.common.rest.FutureApiVersionException;
import be.tribersoft.common.rest.IncorrectApiVersionException;

@RunWith(MockitoJUnitRunner.class)
public class ApiResourceGetWithVersionTest {

	private static final String API_VERSION = "2.1.0";
	private static final String INCOMPATIBLE_VERSION = "1.0.0";
	private static final String FUTURE_VERSION = "2.1.1";

	@InjectMocks
	private ApiResource apiResource;

	@Mock
	private ApiHateoasBuilder apiHateoasBuilder;

	@Mock
	private Resource<ApiToJsonAdapter> apiToJsoAdapterResource;

	@Before
	public void setUp() {
		Whitebox.setInternalState(apiResource, "apiVersion", API_VERSION);
		apiResource.init();
		when(apiHateoasBuilder.build(API_VERSION)).thenReturn(apiToJsoAdapterResource);
	}

	@Test(expected = IncorrectApiVersionException.class)
	public void failsWhenVersionIsNotCompatible() {
		apiResource.getWithVersion(INCOMPATIBLE_VERSION);
	}

	@Test(expected = FutureApiVersionException.class)
	public void failsWhenVersionIsInFuture() {
		apiResource.getWithVersion(FUTURE_VERSION);
	}

	@Test
	public void returnsApi() {
		Resource<ApiToJsonAdapter> resource = apiResource.getWithVersion(API_VERSION);

		assertThat(resource).isSameAs(apiToJsoAdapterResource);
	}
}
