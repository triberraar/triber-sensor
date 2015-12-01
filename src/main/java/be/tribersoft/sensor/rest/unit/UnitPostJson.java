package be.tribersoft.sensor.rest.unit;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;

import be.tribersoft.sensor.domain.api.unit.UnitMessage;

public class UnitPostJson implements UnitMessage {

	@JsonProperty
	private String name;
	@JsonProperty
	private Optional<String> symbol;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Optional<String> getSymbol() {
		if (symbol == null) {
			symbol = Optional.empty();
		}
		return symbol;
	}

}
