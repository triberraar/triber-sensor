package be.tribersoft.sensor.service.impl.type;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.type.TypeFacade;

@RunWith(MockitoJUnitRunner.class)
public class TypeServiceImplDeleteTest {

	private static final long VERSION = 2L;
	private static final String ID = "id";
	@InjectMocks
	private TypeServiceImpl typeService;
	@Mock
	private TypeFacade typeFacade;

	@Test
	public void delegatesToFacade() {
		typeService.delete(ID, VERSION);

		verify(typeFacade).delete(ID, VERSION);
	}

}
