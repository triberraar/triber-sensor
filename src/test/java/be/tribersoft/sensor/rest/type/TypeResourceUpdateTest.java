package be.tribersoft.sensor.rest.type;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.service.api.type.TypeService;

@RunWith(MockitoJUnitRunner.class)
public class TypeResourceUpdateTest {
	private static final long VERSION = 2L;
	private static final String ID = "id";
	@InjectMocks
	private TypeResource typeResource;
	@Mock
	private TypeService typeService;
	@Mock
	private TypeUpdateJson typeUpdateJson;

	@Before
	public void setUp() {
		when(typeUpdateJson.getVersion()).thenReturn(VERSION);
	}

	@Test
	public void delegatesToService() {
		typeResource.update(ID, typeUpdateJson);

		verify(typeService).update(ID, VERSION, typeUpdateJson);
	}
}
