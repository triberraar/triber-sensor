package be.tribersoft.common.testData;

import java.util.Optional;

import be.tribersoft.sensor.domain.impl.device.DeviceEntity;
import be.tribersoft.sensor.domain.impl.sensor.SensorEntity;
import be.tribersoft.sensor.domain.impl.sensor.SensorJpaRepository;
import be.tribersoft.sensor.domain.impl.type.TypeEntity;
import be.tribersoft.sensor.domain.impl.unit.UnitEntity;

public class SensorBuilder {
	private static final String NAME = "name";
	private static final Optional<String> DESCRIPTION = Optional.of("description");

	private String name = NAME;
	private Optional<String> description = DESCRIPTION;
	private UnitEntity unitEntity;
	private TypeEntity typeEntity;
	private DeviceEntity deviceEntity;

	public static SensorBuilder aSensor() {
		return new SensorBuilder();
	}

	public SensorBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public SensorBuilder withDescription(Optional<String> description) {
		this.description = description;
		return this;
	}

	public SensorBuilder withUnit(UnitEntity unitEntity) {
		this.unitEntity = unitEntity;
		return this;
	}

	public SensorBuilder withType(TypeEntity typeEntity) {
		this.typeEntity = typeEntity;
		return this;
	}

	public SensorBuilder withDevice(DeviceEntity deviceEntity) {
		this.deviceEntity = deviceEntity;
		return this;
	}

	public SensorEntity build() {
		SensorEntity sensorEntity = new SensorEntity(name, deviceEntity, typeEntity, unitEntity);
		sensorEntity.setDescription(description);
		return sensorEntity;
	}

	public SensorEntity buildPersistent(SensorJpaRepository repository) {
		SensorEntity sensorEntity = build();
		repository.save(sensorEntity);
		return sensorEntity;
	}

}
