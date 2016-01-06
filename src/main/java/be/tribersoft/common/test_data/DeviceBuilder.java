package be.tribersoft.common.test_data;

import java.util.Optional;

import be.tribersoft.sensor.domain.impl.device.DeviceEntity;
import be.tribersoft.sensor.domain.impl.device.DeviceJpaRepository;

public class DeviceBuilder {
	private static final String NAME = "name";
	private static final Optional<String> DESCRIPTION = Optional.of("description");
	private static final Optional<String> LOCATION = Optional.of("location");

	private String name = NAME;
	private Optional<String> description = DESCRIPTION;
	private Optional<String> location = LOCATION;

	public static DeviceBuilder aDevice() {
		return new DeviceBuilder();
	}

	public DeviceBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public DeviceBuilder withDescription(Optional<String> description) {
		this.description = description;
		return this;
	}

	public DeviceBuilder withLocation(Optional<String> location) {
		this.location = location;
		return this;
	}

	public DeviceEntity build() {
		DeviceEntity deviceEntity = new DeviceEntity(name);
		deviceEntity.setDescription(description);
		deviceEntity.setLocation(location);
		return deviceEntity;
	}

	public DeviceEntity buildPersistent(DeviceJpaRepository repository) {
		DeviceEntity deviceEntity = build();
		repository.save(deviceEntity);
		return deviceEntity;
	}

}
