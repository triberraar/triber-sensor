package be.tribersoft.sensor.rest.sensorReading;

import java.math.BigDecimal;

import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonProperty;

import be.tribersoft.sensor.domain.api.sensorReading.SensorReading;

@Relation(collectionRelation = SensorReadingToJsonAdapter.SENSOR_READINGS, value = SensorReadingToJsonAdapter.SENSOR_READING)
public class SensorReadingToJsonAdapter {

	public static final String SENSOR_READINGS = "sensorReadings";
	public static final String SENSOR_READING = "sensorReading";

	private SensorReading sensorReading;

	public SensorReadingToJsonAdapter(SensorReading sensorReading) {
		this.sensorReading = sensorReading;
	}

	@JsonProperty
	public String getId() {
		return sensorReading.getId();
	}

	@JsonProperty
	public Long getVersion() {
		return sensorReading.getVersion();
	}

	@JsonProperty
	public BigDecimal getValue() {
		return sensorReading.getValue();
	}

}
