package be.tribersoft.sensor.rest.reading;

import java.math.BigDecimal;

import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonProperty;

import be.tribersoft.sensor.domain.api.reading.Reading;

@Relation(collectionRelation = ReadingToJsonAdapter.READINGS, value = ReadingToJsonAdapter.READING)
public class ReadingToJsonAdapter {

	public static final String READINGS = "readings";
	public static final String READING = "reading";

	private Reading reading;

	public ReadingToJsonAdapter(Reading reading) {
		this.reading = reading;
	}

	@JsonProperty
	public String getId() {
		return reading.getId();
	}

	@JsonProperty
	public Long getVersion() {
		return reading.getVersion();
	}

	@JsonProperty
	public BigDecimal getValue() {
		return reading.getValue();
	}

}
