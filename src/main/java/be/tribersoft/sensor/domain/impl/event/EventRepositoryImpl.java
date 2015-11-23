package be.tribersoft.sensor.domain.impl.event;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import be.tribersoft.sensor.domain.api.event.EventRepository;

@Named
public class EventRepositoryImpl implements EventRepository {

	@Inject
	private EventJpaRepository eventJpaRepository;

	public void save(EventDocument eventDocument) {
		eventJpaRepository.save(eventDocument);
	}

	@Override
	public Page<EventDocument> all(Pageable pageable) {
		return eventJpaRepository.findAll(new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(Direction.DESC, "creationDate")));
	}

}
