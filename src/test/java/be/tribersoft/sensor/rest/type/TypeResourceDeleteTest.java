package be.tribersoft.sensor.rest.type;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.rest.VersionValidator;
import be.tribersoft.sensor.service.api.type.TypeService;

@RunWith(MockitoJUnitRunner.class)
public class TypeResourceDeleteTest {
	private static final String API_VERSION = "apiVersion";
	private static final long VERSION = 2L;
	private static final String ID = "id";
	@InjectMocks
	private TypeResource typeResource;
	@Mock
	private TypeService typeService;
	@Mock
	private TypeDeleteJson typeDeleteJson;
	@Mock
	private VersionValidator versionValidator;

	@Before
	public void setUp() {
		when(typeDeleteJson.getVersion()).thenReturn(VERSION);
	}

	@Test
	public void delegatesToService() {
		typeResource.delete(API_VERSION, ID, typeDeleteJson);

		verify(typeService).delete(ID, VERSION);
		verify(versionValidator).validate(API_VERSION);
	}
}
