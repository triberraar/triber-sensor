package be.tribersoft.sensor.domain.api;

import java.util.List;

public interface TypeRepository {
	List<? extends Type> all();

	Type getById(String id);
}
