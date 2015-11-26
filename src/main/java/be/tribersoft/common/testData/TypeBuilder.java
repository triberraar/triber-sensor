package be.tribersoft.common.testData;

import be.tribersoft.sensor.domain.impl.type.TypeEntity;
import be.tribersoft.sensor.domain.impl.type.TypeJpaRepository;

public class TypeBuilder {
	private static final String NAME = "unit";

	private String name = NAME;

	public static TypeBuilder aType() {
		return new TypeBuilder();
	}

	public TypeBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public TypeEntity build() {
		TypeEntity typeEntity = new TypeEntity(name);
		return typeEntity;
	}

	public TypeEntity buildPersistent(TypeJpaRepository typeJpaRepository) {
		TypeEntity result = build();
		typeJpaRepository.save(result);
		return result;
	}

}
