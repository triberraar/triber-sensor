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
public class TypeResourcePatchTest {
	private static final long VERSION = 2L;
	private static final String ID = "id";
	@InjectMocks
	private TypeResource typeResource;
	@Mock
	private TypeService typeService;
	@Mock
	private TypePatchJson typePatchJson;

	@Before
	public void setUp() {
		when(typePatchJson.getVersion()).thenReturn(VERSION);
	}

	@Test
	public void delegatesToService() {
		typeResource.patch(ID, typePatchJson);

		verify(typeService).patch(ID, VERSION, typePatchJson);
	}
}
