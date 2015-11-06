package be.tribersoft.sensor.domain.impl.sensor;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.sensor.domain.api.sensor.SensorFacade;
import be.tribersoft.sensor.domain.api.sensor.SensorMessage;
import be.tribersoft.sensor.domain.api.sensor.SensorUpdateMessage;

@Named
public class SensorFacadeImpl implements SensorFacade {

	@Inject
	private SensorFactory sensorFactory;
	@Inject
	private SensorRepositoryImpl sensorRepository;
	@Inject
	private SensorUpdater sensorUpdater;

	@Override
	public void save(SensorMessage sensorMessage) {
		sensorRepository.save(sensorFactory.create(sensorMessage));
	}

	@Override
	public void update(String id, Long version, SensorUpdateMessage sensorUpdateMessage) {
		SensorEntity sensor = sensorRepository.getByIdAndVersion(id, version);
		sensorUpdater.update(sensor, sensorUpdateMessage);
	}

	@Override
	public void delete(String id, Long version) {
		SensorEntity sensor = sensorRepository.getByIdAndVersion(id, version);
		sensorRepository.delete(sensor);
	}

}
