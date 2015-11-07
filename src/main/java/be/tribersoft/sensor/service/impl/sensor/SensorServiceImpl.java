package be.tribersoft.sensor.service.impl.sensor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import be.tribersoft.sensor.domain.api.sensor.SensorFacade;
import be.tribersoft.sensor.domain.api.sensor.SensorMessage;
import be.tribersoft.sensor.domain.api.sensor.SensorUpdateMessage;
import be.tribersoft.sensor.service.api.sensor.SensorService;

@Named
@Transactional
public class SensorServiceImpl implements SensorService {

	@Inject
	private SensorFacade sensorFacade;

	@Override
	public void save(String deviceId, SensorMessage sensorMessage) {
		sensorFacade.save(deviceId, sensorMessage);
	}

	@Override
	public void update(String deviceId, String id, Long version, SensorUpdateMessage sensorUpdateMessage) {
		sensorFacade.update(deviceId, id, version, sensorUpdateMessage);
	}

	@Override
	public void delete(String deviceId, String id, Long version) {
		sensorFacade.delete(deviceId, id, version);
	}

}
