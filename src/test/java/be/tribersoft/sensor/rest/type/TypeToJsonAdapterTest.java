package be.tribersoft.sensor.rest.type;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.type.Type;

@RunWith(MockitoJUnitRunner.class)
public class TypeToJsonAdapterTest {

	private static final String NAME = "name";
	private static final long VERSION = 2L;
	private static final String ID = "id";
	@InjectMocks
	private TypeToJsonAdapter typeToJsonAdapter;
	@Mock
	private Type type;

	@Before
	public void setUp() {
		when(type.getId()).thenReturn(ID);
		when(type.getVersion()).thenReturn(VERSION);
		when(type.getName()).thenReturn(NAME);
	}

	@Test
	public void delegatesToType() {
		assertThat(typeToJsonAdapter.getId()).isEqualTo(ID);
		assertThat(typeToJsonAdapter.getVersion()).isEqualTo(VERSION);
		assertThat(typeToJsonAdapter.getName()).isEqualTo(NAME);
	}
}
