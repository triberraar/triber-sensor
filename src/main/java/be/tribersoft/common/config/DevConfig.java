package be.tribersoft.common.config;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import be.tribersoft.sensor.domain.impl.event.EventJpaRepository;

@Configuration
@Profile({ "dev", "test" })
public class DevConfig {

	@Inject
	private EventJpaRepository eventJpaRepository;

	@PostConstruct
	public void init() {
		eventJpaRepository.deleteAll();
	}
}
