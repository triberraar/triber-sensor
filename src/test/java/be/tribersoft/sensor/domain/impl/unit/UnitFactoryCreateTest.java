package be.tribersoft.sensor.domain.impl.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.unit.UnitMessage;

@RunWith(MockitoJUnitRunner.class)
public class UnitFactoryCreateTest {

	private static final Optional<String> SYMBOL = Optional.of("symbol");
	private static final String NAME = "name";

	private UnitFactory unitFactory = new UnitFactory();

	@Mock
	private UnitMessage unitMessage;

	@Before
	public void setUp() {
		when(unitMessage.getName()).thenReturn(NAME);
		when(unitMessage.getSymbol()).thenReturn(SYMBOL);
	}

	@Test
	public void createsAUnitEntity() {
		UnitEntity createdUnitEntity = unitFactory.create(unitMessage);

		assertThat(createdUnitEntity).isNotNull();
		assertThat(createdUnitEntity.getName()).isEqualTo(NAME);
		assertThat(createdUnitEntity.getSymbol()).isEqualTo(SYMBOL);
	}

}
