package be.tribersoft.sensor.domain.impl.type;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.type.TypeMessage;

@RunWith(MockitoJUnitRunner.class)
public class TypeUpdaterUpdateTest {

	private static final String NAME = "name";
	private TypeUpdater updater = new TypeUpdater();
	@Mock
	private TypeEntity type;
	@Mock
	private TypeMessage typeMessage;

	@Before
	public void setup() {
		when(typeMessage.getName()).thenReturn(NAME);
	}

	@Test
	public void updatesType() {
		updater.update(type, typeMessage);

		verify(type).setName(NAME);
	}
}
