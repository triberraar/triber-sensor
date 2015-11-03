package be.tribersoft.sensor.rest.sensor;

import java.util.Optional;

import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonProperty;

import be.tribersoft.sensor.domain.api.sensor.Sensor;

@Relation(collectionRelation = "sensors", value = "sensor")
public class SensorToJsonAdapter {

	private Sensor sensor;

	public SensorToJsonAdapter(Sensor sensor) {
		this.sensor = sensor;
	}

	@JsonProperty
	public String getId() {
		return sensor.getId();
	}

	@JsonProperty
	public Long getVersion() {
		return sensor.getVersion();
	}

	@JsonProperty
	public String getName() {
		return sensor.getName();
	}

	@JsonProperty
	public Optional<String> getDescription() {
		return sensor.getDescription();
	}

}
