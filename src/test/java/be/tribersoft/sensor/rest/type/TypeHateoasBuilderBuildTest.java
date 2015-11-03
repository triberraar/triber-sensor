package be.tribersoft.sensor.rest.type;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import be.tribersoft.sensor.domain.api.type.Type;

@RunWith(MockitoJUnitRunner.class)
public class TypeHateoasBuilderBuildTest {

	private static Long VERSION_1 = 0l;
	private static Long VERSION_2 = 1l;
	private static String ID_1 = "id1";
	private static String ID_2 = "id2";
	private static String NAME_1 = "name1";
	private static String NAME_2 = "name2";

	protected MockHttpServletRequest request;

	private TypeHateoasBuilder builder = new TypeHateoasBuilder();

	@Mock
	private Type type1, type2;

	@Before
	public void setUp() {
		request = new MockHttpServletRequest();
		ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
		RequestContextHolder.setRequestAttributes(requestAttributes);

		when(type1.getId()).thenReturn(ID_1);
		when(type1.getName()).thenReturn(NAME_1);
		when(type1.getVersion()).thenReturn(VERSION_1);
		when(type2.getId()).thenReturn(ID_2);
		when(type2.getName()).thenReturn(NAME_2);
		when(type2.getVersion()).thenReturn(VERSION_2);
	}

	@Test
	public void buildsLinksForAType() {
		Resource<TypeToJsonAdapter> typeResource = builder.build(type1);

		TypeToJsonAdapter content = typeResource.getContent();
		assertThat(content.getId()).isEqualTo(ID_1);
		assertThat(content.getVersion()).isEqualTo(VERSION_1);
		assertThat(content.getName()).isEqualTo(NAME_1);
		List<Link> links = typeResource.getLinks();
		assertThat(links.size()).isEqualTo(1);
		assertThat(links.get(0).getRel()).isEqualTo(Link.REL_SELF);
		assertThat(links.get(0).getHref()).endsWith("/api/admin/type/" + ID_1);
	}

	@Test
	public void buildsLinksForTypes() {
		Resources<Resource<TypeToJsonAdapter>> typeResources = builder.build(Arrays.asList(type1, type2));

		List<Link> links = typeResources.getLinks();
		assertThat(links.size()).isEqualTo(1);
		assertThat(links.get(0).getRel()).isEqualTo(Link.REL_SELF);
		assertThat(links.get(0).getHref()).endsWith("/api/admin/type");

		assertThat(typeResources.getContent().size()).isEqualTo(2);
		Collection<Resource<TypeToJsonAdapter>> content = typeResources.getContent();
		Iterator<Resource<TypeToJsonAdapter>> iterator = content.iterator();

		Resource<TypeToJsonAdapter> first = iterator.next();
		assertThat(first.getContent().getId()).isEqualTo(ID_1);
		assertThat(first.getContent().getVersion()).isEqualTo(VERSION_1);
		assertThat(first.getContent().getName()).isEqualTo(NAME_1);
		List<Link> firstLinks = first.getLinks();
		assertThat(firstLinks.size()).isEqualTo(1);
		assertThat(firstLinks.get(0).getRel()).isEqualTo(Link.REL_SELF);
		assertThat(firstLinks.get(0).getHref()).endsWith("/api/admin/type/" + ID_1);

		Resource<TypeToJsonAdapter> second = iterator.next();
		assertThat(second.getContent().getId()).isEqualTo(ID_2);
		assertThat(second.getContent().getVersion()).isEqualTo(VERSION_2);
		assertThat(second.getContent().getName()).isEqualTo(NAME_2);
		List<Link> secondLinks = second.getLinks();
		assertThat(secondLinks.size()).isEqualTo(1);
		assertThat(secondLinks.get(0).getRel()).isEqualTo(Link.REL_SELF);
		assertThat(secondLinks.get(0).getHref()).endsWith("/api/admin/type/" + ID_2);
	}
}
