package be.tribersoft.sensor.domain.api;

import java.util.List;

public interface TypeRepository {
	List<? extends Type> all();

	Type getByUuid(String uuid);
}
