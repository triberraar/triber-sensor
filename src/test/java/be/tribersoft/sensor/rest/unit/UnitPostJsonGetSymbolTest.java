package be.tribersoft.sensor.rest.unit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

public class UnitPostJsonGetSymbolTest {

	private static final String SYMBOL_VALUE = "symbol value";
	private UnitPostJson unitPostJson = new UnitPostJson();

	@Test
	public void returnsEmptyWhenSymbolIsNull() {
		Whitebox.setInternalState(unitPostJson, "symbol", null);

		assertThat(unitPostJson.getSymbol().isPresent()).isFalse();
	}

	@Test
	public void returnsSymbolWhenSymbolIsNotEmpty() {
		Whitebox.setInternalState(unitPostJson, "symbol", Optional.of(SYMBOL_VALUE));

		assertThat(unitPostJson.getSymbol().get()).isEqualTo(SYMBOL_VALUE);

	}

}
