package be.tribersoft.sensor.domain.impl.sensorReading;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.sensor.domain.api.sensorReading.SensorReading;
import be.tribersoft.sensor.domain.api.sensorReading.SensorReadingRepository;

@Named
public class SensorReadingRepositoryImpl implements SensorReadingRepository {
	@Inject
	private SensorReadingJpaRepository sensorReadingJpaRepository;

	public void save(SensorReadingEntity sensor) {
		sensorReadingJpaRepository.save(sensor);
	}

	@Override
	public List<? extends SensorReading> allBySensor(String sensorId) {
		return sensorReadingJpaRepository.findAllBySensorIdOrderByCreationDateDesc(sensorId);
	}

}
