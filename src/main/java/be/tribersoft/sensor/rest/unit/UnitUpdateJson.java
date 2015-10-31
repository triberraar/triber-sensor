package be.tribersoft.sensor.rest.unit;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import be.tribersoft.sensor.domain.api.unit.UnitMessage;

public class UnitUpdateJson implements UnitMessage {

	@JsonProperty
	@NotNull(message = "unit.validation.version.null")
	private Long version;
	@JsonProperty
	private String name;
	@JsonProperty
	private Optional<String> symbol;

	public Long getVersion() {
		return version;
	}

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
