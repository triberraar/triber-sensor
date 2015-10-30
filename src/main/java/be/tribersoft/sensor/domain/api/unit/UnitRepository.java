package be.tribersoft.sensor.domain.api.unit;

import java.util.List;

public interface UnitRepository {
	List<? extends Unit> all();

	Unit getById(String id);
}
