package be.tribersoft.sensor.service.api;

import be.tribersoft.sensor.domain.api.TypeCreate;
import be.tribersoft.sensor.domain.api.TypePatch;
import be.tribersoft.sensor.domain.api.TypeUpdate;

public interface TypeService {

	void save(TypeCreate typeCreate);

	void update(String id, Long version, TypeUpdate typeUpdate);

	void patch(String id, Long version, TypePatch typePatch);

	void delete(String id, Long version);

}
