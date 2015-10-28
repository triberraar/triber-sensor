package be.tribersoft.sensor.domain.api.type;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.impl.type.TypeEntity;

@RunWith(MockitoJUnitRunner.class)
public class TypeUpdaterUpdateTest {

	private static final String NAME = "name";
	private TypeUpdater updater = new TypeUpdater();
	@Mock
	private TypeEntity type;
	@Mock
	private TypeUpdate typeUpdate;

	@Before
	public void setup() {
		when(typeUpdate.getName()).thenReturn(NAME);
	}

	@Test
	public void updatesType() {
		updater.update(type, typeUpdate);

		verify(type).setName(NAME);
	}
}
