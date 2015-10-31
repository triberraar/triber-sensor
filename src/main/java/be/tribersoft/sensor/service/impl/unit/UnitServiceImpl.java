package be.tribersoft.sensor.service.impl.unit;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import be.tribersoft.sensor.domain.api.unit.UnitFacade;
import be.tribersoft.sensor.domain.api.unit.UnitMessage;
import be.tribersoft.sensor.service.api.unit.UnitService;

@Named
@Transactional
public class UnitServiceImpl implements UnitService {

	@Inject
	private UnitFacade unitFacade;

	@Override
	public void save(UnitMessage unitMessage) {
		unitFacade.save(unitMessage);
	}

	@Override
	public void update(String id, Long version, UnitMessage unitMessage) {
		unitFacade.update(id, version, unitMessage);
	}

	@Override
	public void delete(String id, Long version) {
		unitFacade.delete(id, version);
	}

}
