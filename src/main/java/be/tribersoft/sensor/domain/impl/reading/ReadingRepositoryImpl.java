package be.tribersoft.sensor.domain.impl.reading;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.data.domain.Pageable;

import be.tribersoft.sensor.domain.api.reading.ReadingRepository;

@Named
public class ReadingRepositoryImpl implements ReadingRepository {
	@Inject
	private ReadingJpaRepository readingJpaRepository;

	public void save(ReadingEntity sensor) {
		readingJpaRepository.save(sensor);
	}

	@Override
	public List<ReadingEntity> allBySensor(String sensorId, Pageable pageable) {
		return readingJpaRepository.findAllBySensorIdOrderByCreationDateDesc(sensorId, pageable);
	}

	public List<ReadingEntity> allBySensor(String sensorId) {
		return readingJpaRepository.findAllBySensorIdOrderByCreationDateDesc(sensorId);
	}

	public void delete(List<ReadingEntity> readings) {
		readings.parallelStream().forEach(reading -> readingJpaRepository.delete(reading));
	}

	@Override
	public int countBySensor(String sensorId) {
		return readingJpaRepository.countBySensorId(sensorId);
	}

}
