package be.tribersoft.sensor.domain.api.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventRepository {

	Page<? extends Event> all(Pageable pageable);

}
