package be.tribersoft.sensor.domain.impl.type;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.type.TypePatch;
import be.tribersoft.sensor.domain.api.type.TypeUpdater;

@RunWith(MockitoJUnitRunner.class)
public class TypeFacadeImplPatchTest {
	private static final long VERSION = 2L;
	private static final String ID = "id";
	@Mock
	private TypeUpdater typeUpdater;
	@Mock
	private TypeEntity typeEntity;
	@InjectMocks
	private TypeFacadeImpl typeFacadeImpl;
	@Mock
	private TypePatch typePatch;
	@Mock
	private TypeRepositoryImpl TypeRepository;

	@Before
	public void setUp() {
		when(TypeRepository.getByIdAndVersion(ID, VERSION)).thenReturn(typeEntity);
	}

	@Test
	public void patchesTypeEntity() {
		typeFacadeImpl.patch(ID, VERSION, typePatch);

		verify(typeUpdater).patch(typeEntity, typePatch);
	}

}