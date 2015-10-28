package be.tribersoft.sensor.domain.api.type;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.impl.type.TypeEntity;

@RunWith(MockitoJUnitRunner.class)
public class TypeUpdaterPatchTest {

	private static final String NAME = "name";
	private TypeUpdater updater = new TypeUpdater();
	@Mock
	private TypeEntity type;
	@Mock
	private TypePatch typePatch;

	@Test
	public void patchesNameWhenNameProvided() {
		when(typePatch.getName()).thenReturn(Optional.of(NAME));
		updater.patch(type, typePatch);

		verify(type).setName(NAME);
	}

	@Test
	public void doesNothingIfNameNotProvided() {
		when(typePatch.getName()).thenReturn(Optional.ofNullable(null));
		updater.patch(type, typePatch);

		verify(type, never()).setName(anyString());
	}
}
