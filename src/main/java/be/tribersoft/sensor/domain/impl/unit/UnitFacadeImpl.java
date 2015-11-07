package be.tribersoft.sensor.domain.impl.unit;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.sensor.domain.api.sensor.SensorRepository;
import be.tribersoft.sensor.domain.api.unit.UnitFacade;
import be.tribersoft.sensor.domain.api.unit.UnitMessage;
import be.tribersoft.sensor.domain.api.unit.exception.UnitStillInUseException;

@Named
public class UnitFacadeImpl implements UnitFacade {

	@Inject
	private UnitRepositoryImpl unitRepository;
	@Inject
	private UnitFactory unitFactory;
	@Inject
	private UnitUpdater unitUpdater;
	@Inject
	private SensorRepository sensorRepository;

	@Override
	public void save(UnitMessage unitMessage) {
		unitRepository.save(unitFactory.create(unitMessage));
	}

	@Override
	public void update(String id, Long version, UnitMessage unitMessage) {
		UnitEntity unit = unitRepository.getByIdAndVersion(id, version);
		unitUpdater.update(unit, unitMessage);
	}

	@Override
	public void delete(String id, Long version) {
		if (sensorRepository.unitInUse(id)) {
			throw new UnitStillInUseException();
		}
		UnitEntity unit = unitRepository.getByIdAndVersion(id, version);
		unitRepository.delete(unit);
	}

}
