package be.tribersoft.sensor.rest.type;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.service.api.type.TypeService;

@RunWith(MockitoJUnitRunner.class)
public class TypeResourceSaveTest {
	@InjectMocks
	private TypeResource typeResource;
	@Mock
	private TypeService typeService;
	@Mock
	private TypePostJson typePostJson;

	@Test
	public void delegatesToService() {
		typeResource.save(typePostJson);

		verify(typeService).save(typePostJson);
	}
}
