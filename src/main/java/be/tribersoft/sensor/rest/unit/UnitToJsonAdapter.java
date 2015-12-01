package be.tribersoft.sensor.rest.unit;

import java.util.Optional;

import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonProperty;

import be.tribersoft.sensor.domain.api.unit.Unit;

@Relation(collectionRelation = UnitToJsonAdapter.UNITS, value = UnitToJsonAdapter.UNIT)
public class UnitToJsonAdapter {

	public static final String UNITS = "units";
	public static final String UNIT = "unit";

	private Unit unit;

	public UnitToJsonAdapter(Unit unit) {
		this.unit = unit;
	}

	@JsonProperty
	public String getId() {
		return unit.getId();
	}

	@JsonProperty
	public Long getVersion() {
		return unit.getVersion();
	}

	@JsonProperty
	public String getName() {
		return unit.getName();
	}

	@JsonProperty
	public Optional<String> getSymbol() {
		return unit.getSymbol();
	}

}
