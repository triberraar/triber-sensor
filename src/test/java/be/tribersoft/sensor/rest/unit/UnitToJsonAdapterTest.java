package be.tribersoft.sensor.rest.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.unit.Unit;

@RunWith(MockitoJUnitRunner.class)
public class UnitToJsonAdapterTest {

	private static final Optional<String> SYMBOL = Optional.of("symbol");
	private static final String NAME = "name";
	private static final long VERSION = 2L;
	private static final String ID = "id";
	@InjectMocks
	private UnitToJsonAdapter unitToJsonAdapter;
	@Mock
	private Unit unit;

	@Before
	public void setUp() {
		when(unit.getId()).thenReturn(ID);
		when(unit.getVersion()).thenReturn(VERSION);
		when(unit.getName()).thenReturn(NAME);
		when(unit.getSymbol()).thenReturn(SYMBOL);
	}

	@Test
	public void delegatesToUnit() {
		assertThat(unitToJsonAdapter.getId()).isEqualTo(ID);
		assertThat(unitToJsonAdapter.getVersion()).isEqualTo(VERSION);
		assertThat(unitToJsonAdapter.getName()).isEqualTo(NAME);
		assertThat(unitToJsonAdapter.getSymbol()).isEqualTo(SYMBOL);
	}
}
