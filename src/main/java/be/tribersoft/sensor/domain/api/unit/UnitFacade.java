package be.tribersoft.sensor.domain.api.unit;

public interface UnitFacade {

	void save(UnitMessage unitMessage);

	void update(String id, Long version, UnitMessage unitMessage);

	void delete(String id, Long version);
}
