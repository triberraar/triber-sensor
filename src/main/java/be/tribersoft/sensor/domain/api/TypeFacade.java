package be.tribersoft.sensor.domain.api;

public interface TypeFacade {

	void save(Type type);

	void update(String uuid, Long version, TypeUpdate typeUpdate);

	void patch(String uuid, Long version, TypePatch typePatch);

	void delete(String uuid, Long version);
}
