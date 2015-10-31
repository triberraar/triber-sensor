package be.tribersoft.sensor.rest.unit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

public class UnitUpdateJsonGetSymbolTest {

	private static final String SYMBOL_VALUE = "symbol value";
	private UnitUpdateJson unitUpdateJson = new UnitUpdateJson();

	@Test
	public void returnsEmptyWhenSymbolIsNull() {
		Whitebox.setInternalState(unitUpdateJson, "symbol", null);

		assertThat(unitUpdateJson.getSymbol().isPresent()).isFalse();
	}

	@Test
	public void returnsSymbolWhenSymbolIsNotEmpty() {
		Whitebox.setInternalState(unitUpdateJson, "symbol", Optional.of(SYMBOL_VALUE));

		assertThat(unitUpdateJson.getSymbol().get()).isEqualTo(SYMBOL_VALUE);

	}

}
