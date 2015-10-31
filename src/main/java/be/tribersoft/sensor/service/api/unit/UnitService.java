package be.tribersoft.sensor.service.api.unit;

import be.tribersoft.sensor.domain.api.unit.UnitMessage;

public interface UnitService {

	void save(UnitMessage unitMessage);

	void update(String id, Long version, UnitMessage unitMessage);

	void delete(String id, Long version);

}
