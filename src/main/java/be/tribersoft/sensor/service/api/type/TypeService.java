package be.tribersoft.sensor.service.api.type;

import be.tribersoft.sensor.domain.api.type.TypeMessage;

public interface TypeService {

	void save(TypeMessage typeCreate);

	void update(String id, Long version, TypeMessage typeMessage);

	void delete(String id, Long version);

}
