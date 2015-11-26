package be.tribersoft.sensor.rest.event;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import be.tribersoft.common.BeanInjector;
import be.tribersoft.sensor.domain.api.device.Device;
import be.tribersoft.sensor.domain.api.device.DeviceRepository;
import be.tribersoft.sensor.domain.api.event.Event;
import be.tribersoft.sensor.domain.api.event.EventSubjectVisitor;
import be.tribersoft.sensor.domain.api.reading.Reading;
import be.tribersoft.sensor.domain.api.reading.ReadingRepository;
import be.tribersoft.sensor.domain.api.sensor.Sensor;
import be.tribersoft.sensor.domain.api.sensor.SensorRepository;
import be.tribersoft.sensor.rest.device.DeviceResource;
import be.tribersoft.sensor.rest.device.DeviceToJsonAdapter;
import be.tribersoft.sensor.rest.reading.ReadingResource;
import be.tribersoft.sensor.rest.reading.ReadingToJsonAdapter;
import be.tribersoft.sensor.rest.sensor.SensorResource;
import be.tribersoft.sensor.rest.sensor.SensorToJsonAdapter;

public class EventUrlVisitor implements EventSubjectVisitor {
	@Inject
	private SensorRepository sensorRepository;
	@Inject
	private ReadingRepository readingRepository;
	@Inject
	private DeviceRepository deviceRepository;
	@Value("${api.version}")
	private String apiVersion;
	private Optional<Link> link;

	@Override
	public void visitSensor(Event event) {
		BeanInjector.inject(this, sensorRepository, apiVersion);
		if (sensorRepository.exists(event.getEventId())) {
			Sensor sensor = sensorRepository.getById(event.getEventId());
			link = Optional.of(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SensorResource.class).get(apiVersion, sensor.getDevice().getId(), sensor.getId())).withRel(SensorToJsonAdapter.SENSOR));
		} else {
			link = Optional.empty();
		}
	}

	@Override
	public void visitReading(Event event) {
		BeanInjector.inject(this, readingRepository, apiVersion);
		if (readingRepository.exists(event.getEventId())) {
			Reading reading = readingRepository.getById(event.getEventId());
			link = Optional
					.of(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ReadingResource.class).all(apiVersion, reading.getSensor().getDevice().getId(), reading.getSensor().getId(), 0)).withRel(ReadingToJsonAdapter.READINGS));
		} else {
			link = Optional.empty();
		}
	}

	@Override
	public void visitDevice(Event event) {
		BeanInjector.inject(this, deviceRepository, apiVersion);
		if (deviceRepository.exists(event.getEventId())) {
			Device device = deviceRepository.getById(event.getEventId());
			link = Optional.of(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(DeviceResource.class).get(apiVersion, device.getId())).withRel(DeviceToJsonAdapter.DEVICE));
		} else {
			link = Optional.empty();
		}
	}

	public Optional<Link> getLink() {
		return link;
	}

}
