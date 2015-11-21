package be.tribersoft.sensor.domain.impl.event;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class EventRepositoryImpl {

	@Inject
	private EventJpaRepository eventJpaRepository;

	public void save(EventDocument eventDocument) {
		eventJpaRepository.save(eventDocument);
	}
}
