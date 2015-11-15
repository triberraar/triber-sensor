package be.tribersoft.sensor.rest.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ApiHateoasBuilderBuildTest {

	private static final String VERSION = "version";
	private ApiHateoasBuilder builder = new ApiHateoasBuilder();
	protected MockHttpServletRequest request;

	@Before
	public void setUp() {
		request = new MockHttpServletRequest();
		ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
		RequestContextHolder.setRequestAttributes(requestAttributes);
		Whitebox.setInternalState(builder, "apiVersion", VERSION);
	}

	@Test
	public void buildsLinksToApiResourceWithVersion() {
		Resource<ApiToJsonAdapter> result = builder.build(VERSION);

		assertThat(result.getContent().getVersion()).isEqualTo(VERSION);
		List<Link> links = result.getLinks();
		assertThat(links.size()).isEqualTo(4);
		assertThat(links.get(0).getRel()).isEqualTo(Link.REL_SELF);
		assertThat(links.get(0).getHref()).isEqualTo("http://localhost/api/" + VERSION);
		assertThat(links.get(1).getRel()).isEqualTo("types");
		assertThat(links.get(1).getHref()).isEqualTo("http://localhost/api/admin/type");
		assertThat(links.get(2).getRel()).isEqualTo("units");
		assertThat(links.get(2).getHref()).isEqualTo("http://localhost/api/admin/unit");
		assertThat(links.get(3).getRel()).isEqualTo("devices");
		assertThat(links.get(3).getHref()).isEqualTo("http://localhost/api/device");
	}

	@Test
	public void buildsLinksToApiResource() {
		Resource<ApiToJsonAdapter> result = builder.build();

		assertThat(result.getContent().getVersion()).isEqualTo(VERSION);
		List<Link> links = result.getLinks();
		assertThat(links.size()).isEqualTo(2);
		assertThat(links.get(0).getRel()).isEqualTo(Link.REL_SELF);
		assertThat(links.get(0).getHref()).isEqualTo("http://localhost/api");
		assertThat(links.get(1).getRel()).isEqualTo("api");
		assertThat(links.get(1).getHref()).isEqualTo("http://localhost/api/" + VERSION);
	}
}
