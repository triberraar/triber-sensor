package be.tribersoft.common.test_data;

import java.math.BigDecimal;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import be.tribersoft.sensor.domain.impl.device.DeviceEntity;
import be.tribersoft.sensor.domain.impl.device.DeviceJpaRepository;
import be.tribersoft.sensor.domain.impl.event.EventJpaRepository;
import be.tribersoft.sensor.domain.impl.reading.ReadingJpaRepository;
import be.tribersoft.sensor.domain.impl.sensor.SensorEntity;
import be.tribersoft.sensor.domain.impl.sensor.SensorJpaRepository;
import be.tribersoft.sensor.domain.impl.type.TypeEntity;
import be.tribersoft.sensor.domain.impl.type.TypeJpaRepository;
import be.tribersoft.sensor.domain.impl.unit.UnitEntity;
import be.tribersoft.sensor.domain.impl.unit.UnitJpaRepository;

@Configuration
@Profile("dev")
public class DevData {

	@Inject
	private EventJpaRepository eventJpaRepository;

	@Inject
	private UnitJpaRepository unitJpaRepository;
	@Inject
	private TypeJpaRepository typeJpaRepository;
	@Inject
	private DeviceJpaRepository deviceJpaRepository;
	@Inject
	private SensorJpaRepository sensorJpaRepository;
	@Inject
	private ReadingJpaRepository readingJpaRepository;

	@Profile("dev")
	@PostConstruct
	private void populateTestData() {
		eventJpaRepository.deleteAll();
		UnitEntity unitEntity = UnitBuilder.aUnit().buildPersistent(unitJpaRepository);
		TypeEntity typeEntity = TypeBuilder.aType().buildPersistent(typeJpaRepository);
		DeviceEntity deviceEntity1 = DeviceBuilder.aDevice().withName("device 1").withDescription(Optional.of("description 1")).withLocation(Optional.of("location 1")).buildPersistent(deviceJpaRepository);
		DeviceEntity deviceEntity2 = DeviceBuilder.aDevice().withName("device 2").withDescription(Optional.of("description 2")).withLocation(Optional.of("location 2")).buildPersistent(deviceJpaRepository);
		SensorEntity sensorEntity11 = SensorBuilder.aSensor().withType(typeEntity).withUnit(unitEntity).withDevice(deviceEntity1).withName("sensor 1.1").withDescription(Optional.of("description 1.1")).buildPersistent(sensorJpaRepository);
		SensorEntity sensorEntity12 = SensorBuilder.aSensor().withType(typeEntity).withUnit(unitEntity).withDevice(deviceEntity1).withName("sensor 1.2").withDescription(Optional.of("description 1.2")).buildPersistent(sensorJpaRepository);
		SensorEntity sensorEntity21 = SensorBuilder.aSensor().withType(typeEntity).withUnit(unitEntity).withDevice(deviceEntity2).withName("sensor 2.1").withDescription(Optional.of("description 2.1")).buildPersistent(sensorJpaRepository);
		SensorEntity sensorEntity22 = SensorBuilder.aSensor().withType(typeEntity).withUnit(unitEntity).withDevice(deviceEntity2).withName("sensor 2.2").withDescription(Optional.of("description 2.2")).buildPersistent(sensorJpaRepository);
		ReadingBuilder.aReading().withSensor(sensorEntity11).withValue(BigDecimal.valueOf(1.1)).buildPersistent(readingJpaRepository);
		ReadingBuilder.aReading().withSensor(sensorEntity11).withValue(BigDecimal.valueOf(2.1)).buildPersistent(readingJpaRepository);
		ReadingBuilder.aReading().withSensor(sensorEntity12).withValue(BigDecimal.valueOf(-1.1)).buildPersistent(readingJpaRepository);
		ReadingBuilder.aReading().withSensor(sensorEntity12).withValue(BigDecimal.valueOf(-2.1)).buildPersistent(readingJpaRepository);
		ReadingBuilder.aReading().withSensor(sensorEntity21).withValue(BigDecimal.valueOf(100)).buildPersistent(readingJpaRepository);
		ReadingBuilder.aReading().withSensor(sensorEntity21).withValue(BigDecimal.valueOf(123)).buildPersistent(readingJpaRepository);
		ReadingBuilder.aReading().withSensor(sensorEntity22).withValue(BigDecimal.valueOf(199.0986)).buildPersistent(readingJpaRepository);
		ReadingBuilder.aReading().withSensor(sensorEntity22).withValue(BigDecimal.valueOf(-1.00003)).buildPersistent(readingJpaRepository);
	}
}
