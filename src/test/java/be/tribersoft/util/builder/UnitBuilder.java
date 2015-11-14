package be.tribersoft.util.builder;

import java.util.Optional;

import be.tribersoft.sensor.domain.impl.unit.UnitEntity;
import be.tribersoft.sensor.domain.impl.unit.UnitJpaRepository;

public class UnitBuilder {
	private static final String NAME = "unit";
	private static final Optional<String> SYMBOL = Optional.of("symbol");

	private String name = NAME;
	private Optional<String> symbol = SYMBOL;

	public static UnitBuilder aUnit() {
		return new UnitBuilder();
	}

	public UnitBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public UnitBuilder withSymbol(Optional<String> symbol) {
		this.symbol = symbol;
		return this;
	}

	public UnitEntity build() {
		UnitEntity unitEntity = new UnitEntity(name);
		unitEntity.setSymbol(symbol);
		return unitEntity;
	}

	public UnitEntity buildPersistent(UnitJpaRepository unitJpaRepository) {
		UnitEntity result = build();
		unitJpaRepository.save(result);
		return result;
	}

}
