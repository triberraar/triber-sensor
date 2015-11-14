package be.tribersoft.sensor.service.impl.reading;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import be.tribersoft.sensor.domain.api.reading.ReadingFacade;
import be.tribersoft.sensor.domain.api.reading.ReadingMessage;
import be.tribersoft.sensor.service.api.reading.ReadingService;

@Named
@Transactional
public class ReadingServiceImpl implements ReadingService {

	@Inject
	private ReadingFacade readingFacade;

	@Override
	public void save(String sensorId, ReadingMessage readingMessage) {
		readingFacade.save(sensorId, readingMessage);
	}

	@Override
	public void deleteBySensor(String sensorId) {
		readingFacade.deleteBySensor(sensorId);
	}

}
