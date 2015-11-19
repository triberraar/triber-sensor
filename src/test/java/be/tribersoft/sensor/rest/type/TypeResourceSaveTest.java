package be.tribersoft.sensor.rest.type;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.rest.VersionValidator;
import be.tribersoft.sensor.service.api.type.TypeService;

@RunWith(MockitoJUnitRunner.class)
public class TypeResourceSaveTest {
	private static final String API_VERSION = "apiVersion";
	@InjectMocks
	private TypeResource typeResource;
	@Mock
	private TypeService typeService;
	@Mock
	private TypePostJson typePostJson;
	@Mock
	private VersionValidator versionValidator;

	@Test
	public void delegatesToService() {
		typeResource.save(API_VERSION, typePostJson);

		verify(typeService).save(typePostJson);
		verify(versionValidator).validate(API_VERSION);
	}
}
