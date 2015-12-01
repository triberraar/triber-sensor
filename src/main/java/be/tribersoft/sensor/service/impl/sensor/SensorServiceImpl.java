package be.tribersoft.sensor.service.impl.sensor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import be.tribersoft.sensor.domain.api.sensor.SensorFacade;
import be.tribersoft.sensor.domain.api.sensor.SensorMessage;
import be.tribersoft.sensor.domain.api.sensor.SensorRepository;
import be.tribersoft.sensor.domain.api.sensor.SensorUpdateMessage;
import be.tribersoft.sensor.service.api.reading.ReadingService;
import be.tribersoft.sensor.service.api.sensor.SensorService;

@Named
@Transactional
public class SensorServiceImpl implements SensorService {

	@Inject
	private SensorFacade sensorFacade;
	@Inject
	private ReadingService readingService;
	@Inject
	private SensorRepository sensorRepository;

	@Override
	public void save(String deviceId, SensorMessage sensorMessage) {
		sensorFacade.save(deviceId, sensorMessage);
	}

	@Override
	public void update(String id, Long version, SensorUpdateMessage sensorUpdateMessage) {
		sensorFacade.update(id, version, sensorUpdateMessage);
	}

	@Override
	public void delete(String id, Long version) {
		readingService.deleteBySensor(id);
		sensorFacade.delete(id, version);
	}

	@Override
	public void deleteByDevice(String deviceId) {
		sensorRepository.allByDevice(deviceId).parallelStream().forEach(sensor -> delete(sensor.getId(), sensor.getVersion()));
	}

}
