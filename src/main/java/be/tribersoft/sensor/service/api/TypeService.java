package be.tribersoft.sensor.service.api;

import be.tribersoft.sensor.domain.api.Type;
import be.tribersoft.sensor.domain.api.TypePatch;
import be.tribersoft.sensor.domain.api.TypeUpdate;

public interface TypeService {

	void save(Type type);

	void update(String uuid, Long version, TypeUpdate typeUpdate);

	void patch(String uuid, Long version, TypePatch typePatch);

	void delete(String uuid, Long version);

}
