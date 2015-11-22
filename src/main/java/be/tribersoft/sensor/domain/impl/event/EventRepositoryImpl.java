package be.tribersoft.sensor.domain.impl.event;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import be.tribersoft.sensor.domain.api.event.EventRepository;

@Named
public class EventRepositoryImpl implements EventRepository {

	@Inject
	private EventJpaRepository eventJpaRepository;

	public void save(EventDocument eventDocument) {
		eventJpaRepository.save(eventDocument);
	}

	@Override
	public List<EventDocument> all() {
		return eventJpaRepository.findAllByOrderByCreationDateDesc();
	}
}
