package be.tribersoft.sensor.domain.api.type;

public interface TypeFacade {

	void save(TypeCreate typeCreate);

	void update(String id, Long version, TypeUpdate typeUpdate);

	void delete(String id, Long version);
}
