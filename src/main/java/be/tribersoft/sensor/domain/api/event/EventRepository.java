package be.tribersoft.sensor.domain.api.event;

import java.util.List;

public interface EventRepository {

	List<? extends Event> all();

}
