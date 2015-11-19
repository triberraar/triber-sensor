package be.tribersoft.sensor.rest.unit;

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

import be.tribersoft.sensor.domain.api.unit.Unit;
import be.tribersoft.sensor.domain.api.unit.UnitRepository;
import be.tribersoft.sensor.rest.VersionValidator;

@RunWith(MockitoJUnitRunner.class)
public class UnitResourceGetTest {

	private static final String API_VERSION = "apiVersion";

	private static String ID = "id";

	@InjectMocks
	private UnitResource unitResource;
	@Mock
	private UnitRepository unitRepository;
	@Mock
	private Unit unit;
	@Mock
	private UnitHateoasBuilder unitHateosBuilder;
	@Mock
	private Resource<UnitToJsonAdapter> resource;
	@Mock
	private VersionValidator versionValidator;

	@Before
	public void setUp() {
		when(unitRepository.getById(ID)).thenReturn(unit);
		when(unitHateosBuilder.build(unit)).thenReturn(resource);
	}

	@Test
	public void delegatesToService() {
		Resource<UnitToJsonAdapter> returnedResource = unitResource.get(API_VERSION, ID);

		assertThat(returnedResource).isSameAs(resource);
		verify(versionValidator).validate(API_VERSION);
	}

}
