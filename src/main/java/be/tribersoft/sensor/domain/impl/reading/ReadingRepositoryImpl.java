package be.tribersoft.sensor.domain.impl.reading;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import be.tribersoft.sensor.domain.api.reading.Reading;
import be.tribersoft.sensor.domain.api.reading.ReadingRepository;
import be.tribersoft.sensor.domain.api.reading.exception.ReadingNotFoundException;

@Named
public class ReadingRepositoryImpl implements ReadingRepository {
	@Inject
	private ReadingJpaRepository readingJpaRepository;

	public void save(ReadingEntity sensor) {
		readingJpaRepository.save(sensor);
	}

	@Override
	public Page<ReadingEntity> allBySensor(String sensorId, Pageable pageable) {
		return readingJpaRepository.findAllBySensorIdOrderByCreationDateDesc(sensorId, pageable);
	}

	public List<ReadingEntity> allBySensor(String sensorId) {
		return readingJpaRepository.findAllBySensorIdOrderByCreationDateDesc(sensorId);
	}

	public void delete(List<ReadingEntity> readings) {
		readings.parallelStream().forEach(reading -> readingJpaRepository.delete(reading));
	}

	@Override
	public Reading getById(String id) {
		Optional<ReadingEntity> reading = readingJpaRepository.findById(id);
		if (!reading.isPresent()) {
			throw new ReadingNotFoundException();
		}
		return reading.get();
	}

	@Override
	public boolean exists(String id) {
		return readingJpaRepository.exists(id);
	}

}
