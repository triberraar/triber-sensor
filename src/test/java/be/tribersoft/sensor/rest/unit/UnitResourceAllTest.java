package be.tribersoft.sensor.rest.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
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

import be.tribersoft.sensor.domain.api.unit.Unit;
import be.tribersoft.sensor.domain.api.unit.UnitRepository;

@RunWith(MockitoJUnitRunner.class)
public class UnitResourceAllTest {

	@InjectMocks
	private UnitResource unitResource;
	@Mock
	private UnitRepository unitRepository;
	@Mock
	private Unit unit1, unit2;
	@Mock
	private UnitHateoasBuilder unitHateosBuilder;
	@Mock
	private Resources<Resource<UnitToJsonAdapter>> resources;

	@Before
	public void setUp() {
		doReturn(Arrays.<Unit> asList(unit1, unit2)).when(unitRepository).all();
		when(unitHateosBuilder.build(Arrays.asList(unit1, unit2))).thenReturn(resources);
	}

	@Test
	public void delegatesToService() {
		Resources<Resource<UnitToJsonAdapter>> unitResources = unitResource.all();

		assertThat(unitResources).isSameAs(resources);
	}

}
