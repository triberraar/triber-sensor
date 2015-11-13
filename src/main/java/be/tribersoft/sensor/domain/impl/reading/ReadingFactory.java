package be.tribersoft.sensor.domain.impl.reading;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.sensor.domain.api.reading.ReadingMessage;
import be.tribersoft.sensor.domain.impl.sensor.SensorRepositoryImpl;

@Named
public class ReadingFactory {

	@Inject
	private SensorRepositoryImpl sensorRepository;

	public ReadingEntity create(String sensorId, ReadingMessage readingMessage) {
		ReadingEntity readingEntity = new ReadingEntity(readingMessage.getValue(), sensorRepository.getById(sensorId));
		return readingEntity;
	}

}
