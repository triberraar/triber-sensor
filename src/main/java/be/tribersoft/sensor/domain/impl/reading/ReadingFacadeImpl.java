package be.tribersoft.sensor.domain.impl.reading;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.sensor.domain.api.reading.ReadingFacade;
import be.tribersoft.sensor.domain.api.reading.ReadingMessage;

@Named
public class ReadingFacadeImpl implements ReadingFacade {

	@Inject
	private ReadingFactory readingFactory;
	@Inject
	private ReadingRepositoryImpl readingRepository;

	@Override
	public void save(String sensorId, ReadingMessage readingMessage) {
		readingRepository.save(readingFactory.create(sensorId, readingMessage));
	}

}
