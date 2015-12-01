package be.tribersoft.sensor.service.impl.type;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.type.TypeFacade;
import be.tribersoft.sensor.domain.api.type.TypeMessage;

@RunWith(MockitoJUnitRunner.class)
public class TypeServiceImplUpdateTest {

	private static final long VERSION = 2L;
	private static final String ID = "id";
	@InjectMocks
	private TypeServiceImpl typeService;
	@Mock
	private TypeFacade typeFacade;
	@Mock
	private TypeMessage typeMessage;

	@Test
	public void delegatesToFacade() {
		typeService.update(ID, VERSION, typeMessage);

		verify(typeFacade).update(ID, VERSION, typeMessage);
	}

}
