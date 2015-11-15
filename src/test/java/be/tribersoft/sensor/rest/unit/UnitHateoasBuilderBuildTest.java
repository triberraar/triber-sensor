package be.tribersoft.sensor.rest.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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

import be.tribersoft.sensor.domain.api.unit.Unit;

@RunWith(MockitoJUnitRunner.class)
public class UnitHateoasBuilderBuildTest {
	private static Long VERSION_1 = 0l;
	private static Long VERSION_2 = 1l;
	private static String ID_1 = "id1";
	private static String ID_2 = "id2";
	private static String NAME_1 = "name1";
	private static String NAME_2 = "name2";
	private static String SYMBOL_1 = "symbol1";

	protected MockHttpServletRequest request;

	private UnitHateoasBuilder builder = new UnitHateoasBuilder();

	@Mock
	private Unit unit1, unit2;

	@Before
	public void setUp() {
		request = new MockHttpServletRequest();
		ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
		RequestContextHolder.setRequestAttributes(requestAttributes);

		when(unit1.getId()).thenReturn(ID_1);
		when(unit1.getName()).thenReturn(NAME_1);
		when(unit1.getVersion()).thenReturn(VERSION_1);
		when(unit1.getSymbol()).thenReturn(Optional.of(SYMBOL_1));
		when(unit2.getId()).thenReturn(ID_2);
		when(unit2.getName()).thenReturn(NAME_2);
		when(unit2.getVersion()).thenReturn(VERSION_2);
		when(unit2.getSymbol()).thenReturn(Optional.ofNullable(null));
	}

	@Test
	public void buildsLinksForAUnit() {
		Resource<UnitToJsonAdapter> unitResource = builder.build(unit1);

		UnitToJsonAdapter content = unitResource.getContent();
		assertThat(content.getId()).isEqualTo(ID_1);
		assertThat(content.getVersion()).isEqualTo(VERSION_1);
		assertThat(content.getName()).isEqualTo(NAME_1);
		assertThat(content.getSymbol().get()).isEqualTo(SYMBOL_1);
		List<Link> links = unitResource.getLinks();
		assertThat(links.size()).isEqualTo(1);
		assertThat(links.get(0).getRel()).isEqualTo(Link.REL_SELF);
		assertThat(links.get(0).getHref()).isEqualTo("http://localhost/api/admin/unit/" + ID_1);
	}

	@Test
	public void buildsLinksForUnits() {
		Resources<Resource<UnitToJsonAdapter>> unitResources = builder.build(Arrays.asList(unit1, unit2));

		List<Link> links = unitResources.getLinks();
		assertThat(links.size()).isEqualTo(1);
		assertThat(links.get(0).getRel()).isEqualTo(Link.REL_SELF);
		assertThat(links.get(0).getHref()).isEqualTo("http://localhost/api/admin/unit");

		assertThat(unitResources.getContent().size()).isEqualTo(2);
		Collection<Resource<UnitToJsonAdapter>> content = unitResources.getContent();
		Iterator<Resource<UnitToJsonAdapter>> iterator = content.iterator();

		Resource<UnitToJsonAdapter> first = iterator.next();
		assertThat(first.getContent().getId()).isEqualTo(ID_1);
		assertThat(first.getContent().getVersion()).isEqualTo(VERSION_1);
		assertThat(first.getContent().getName()).isEqualTo(NAME_1);
		assertThat(first.getContent().getSymbol().get()).isEqualTo(SYMBOL_1);
		List<Link> firstLinks = first.getLinks();
		assertThat(firstLinks.size()).isEqualTo(1);
		assertThat(firstLinks.get(0).getRel()).isEqualTo(Link.REL_SELF);
		assertThat(firstLinks.get(0).getHref()).isEqualTo("http://localhost/api/admin/unit/" + ID_1);

		Resource<UnitToJsonAdapter> second = iterator.next();
		assertThat(second.getContent().getId()).isEqualTo(ID_2);
		assertThat(second.getContent().getVersion()).isEqualTo(VERSION_2);
		assertThat(second.getContent().getName()).isEqualTo(NAME_2);
		assertThat(second.getContent().getSymbol().isPresent()).isFalse();
		List<Link> secondLinks = second.getLinks();
		assertThat(secondLinks.size()).isEqualTo(1);
		assertThat(secondLinks.get(0).getRel()).isEqualTo(Link.REL_SELF);
		assertThat(secondLinks.get(0).getHref()).isEqualTo("http://localhost/api/admin/unit/" + ID_2);
	}
}
