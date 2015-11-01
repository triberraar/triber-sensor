package be.tribersoft.sensor.service.impl.sensor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import be.tribersoft.sensor.domain.api.sensor.SensorFacade;
import be.tribersoft.sensor.domain.api.sensor.SensorMessage;
import be.tribersoft.sensor.service.api.sensor.SensorService;

@Named
@Transactional
public class SensorServiceImpl implements SensorService {

	@Inject
	private SensorFacade sensorFacade;

	@Override
	public void save(SensorMessage sensorMessage) {
		sensorFacade.save(sensorMessage);
	}

	@Override
	public void update(String id, Long version, SensorMessage sensorMessage) {
		sensorFacade.update(id, version, sensorMessage);
	}

	@Override
	public void delete(String id, Long version) {
		sensorFacade.delete(id, version);
	}

}
