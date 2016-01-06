package be.tribersoft.common.test_data;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import be.tribersoft.sensor.domain.impl.event.EventJpaRepository;

@Configuration
@Profile("test")
public class TestData {

	@Inject
	private EventJpaRepository eventJpaRepository;

	@PostConstruct
	private void clear() {
		eventJpaRepository.deleteAll();
	}

}
