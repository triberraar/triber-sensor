package be.tribersoft.sensor.rest.device;

import java.util.Optional;

import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonProperty;

import be.tribersoft.sensor.domain.api.device.Device;

@Relation(collectionRelation = DeviceToJsonAdapter.DEVICES, value = DeviceToJsonAdapter.DEVICE)
public class DeviceToJsonAdapter {

	public static final String DEVICES = "devices";
	public static final String DEVICE = "device";

	private Device device;

	public DeviceToJsonAdapter(Device device) {
		this.device = device;
	}

	@JsonProperty
	public String getId() {
		return device.getId();
	}

	@JsonProperty
	public Long getVersion() {
		return device.getVersion();
	}

	@JsonProperty
	public String getName() {
		return device.getName();
	}

	@JsonProperty
	public Optional<String> getDescription() {
		return device.getDescription();
	}

	@JsonProperty
	public Optional<String> getLocation() {
		return device.getLocation();
	}

}
