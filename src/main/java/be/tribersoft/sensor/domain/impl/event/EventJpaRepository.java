package be.tribersoft.sensor.domain.impl.event;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EventJpaRepository extends ElasticsearchRepository<EventDocument, String> {

	List<EventDocument> findAllByOrderByCreationDateDesc();
}
