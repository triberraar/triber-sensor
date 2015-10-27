package be.tribersoft.sensor.domain.api.type;

public interface TypeFacade {

	void save(TypeCreate typeCreate);

	void update(String id, Long version, TypeUpdate typeUpdate);

	void patch(String id, Long version, TypePatch typePatch);

	void delete(String id, Long version);
}
