package be.tribersoft.sensor.domain.impl.event;

import javax.persistence.PostPersist;

import be.tribersoft.sensor.domain.impl.reading.ReadingEntity;

public class EventPersister {

	@PostPersist
	public void postPersist(Object entity) {
		if (entity instanceof ReadingEntity)
			System.out.println("post persisting " + ((ReadingEntity) entity).getId());
	}
}
