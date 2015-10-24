package be.tribersoft.sensor.service.impl.type;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.type.TypeCreate;
import be.tribersoft.sensor.domain.api.type.TypeFacade;

@RunWith(MockitoJUnitRunner.class)
public class TypeServiceImplSaveTest {

	@InjectMocks
	private TypeServiceImpl typeService;
	@Mock
	private TypeFacade typeFacade;
	@Mock
	private TypeCreate typeCreate;

	@Test
	public void delegatesToFacade() {
		typeService.save(typeCreate);

		verify(typeFacade).save(typeCreate);
	}

}
