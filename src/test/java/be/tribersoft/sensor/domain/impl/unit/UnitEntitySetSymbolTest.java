package be.tribersoft.sensor.domain.impl.unit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

public class UnitEntitySetSymbolTest {

	private static final String SYMBOL = "symbol";
	private static final String NAME = "name";
	private UnitEntity unitEntity = new UnitEntity(NAME);

	@Test
	public void setsNullSymbol() {
		unitEntity.setSymbol(Optional.empty());

		assertThat(unitEntity.getSymbol().isPresent()).isFalse();
	}

	@Test
	public void setsNotNullSymbol() {
		unitEntity.setSymbol(Optional.of(SYMBOL));

		assertThat(unitEntity.getSymbol().isPresent()).isTrue();
		assertThat(unitEntity.getSymbol().get()).isEqualTo(SYMBOL);
	}

}
