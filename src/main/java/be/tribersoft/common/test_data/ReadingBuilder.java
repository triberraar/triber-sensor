package be.tribersoft.common.test_data;

import java.math.BigDecimal;

import be.tribersoft.sensor.domain.impl.reading.ReadingEntity;
import be.tribersoft.sensor.domain.impl.reading.ReadingJpaRepository;
import be.tribersoft.sensor.domain.impl.sensor.SensorEntity;

public class ReadingBuilder {

	private static final BigDecimal VALUE = BigDecimal.valueOf(1.2);

	private BigDecimal value = VALUE;
	private SensorEntity sensor;

	public static ReadingBuilder aReading() {
		return new ReadingBuilder();
	}

	public ReadingBuilder withValue(BigDecimal value) {
		this.value = value;
		return this;
	}

	public ReadingBuilder withSensor(SensorEntity sensor) {
		this.sensor = sensor;
		return this;
	}

	public ReadingEntity build() {
		return new ReadingEntity(value, sensor);
	}

	public ReadingEntity buildPersistent(ReadingJpaRepository readingJpaRepository) {
		ReadingEntity readingEntity = build();
		readingJpaRepository.save(readingEntity);
		return readingEntity;
	}

}
