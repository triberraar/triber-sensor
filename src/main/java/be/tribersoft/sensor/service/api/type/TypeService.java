package be.tribersoft.sensor.service.api.type;

import be.tribersoft.sensor.domain.api.type.TypeCreate;
import be.tribersoft.sensor.domain.api.type.TypeUpdate;

public interface TypeService {

	void save(TypeCreate typeCreate);

	void update(String id, Long version, TypeUpdate typeUpdate);

	void delete(String id, Long version);

}
