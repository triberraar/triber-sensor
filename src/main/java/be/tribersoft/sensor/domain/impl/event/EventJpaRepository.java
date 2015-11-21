package be.tribersoft.sensor.domain.impl.event;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EventJpaRepository extends ElasticsearchRepository<EventDocument, String> {

}
