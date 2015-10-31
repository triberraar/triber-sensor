package be.tribersoft.sensor.domain.api.type;

public interface TypeFacade {

	void save(TypeMessage typeMessage);

	void update(String id, Long version, TypeMessage typeMessage);

	void delete(String id, Long version);
}
