package be.tribersoft.sensor.rest.device;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import be.tribersoft.sensor.domain.api.device.Device;
import be.tribersoft.sensor.rest.sensor.SensorDeviceResource;
import be.tribersoft.sensor.rest.sensor.SensorToJsonAdapter;

@Named
public class DeviceHateoasBuilder {

	public Resource<DeviceToJsonAdapter> build(Device device) {
		Resource<DeviceToJsonAdapter> resource = new Resource<DeviceToJsonAdapter>(new DeviceToJsonAdapter(device), ControllerLinkBuilder.linkTo(DeviceResource.class).slash(device).withSelfRel());
		resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SensorDeviceResource.class).all(device.getId())).withRel(SensorToJsonAdapter.SENSORS));
		return resource;
	}

	public Resources<Resource<DeviceToJsonAdapter>> build(List<? extends Device> devices) {
		List<Resource<DeviceToJsonAdapter>> transformedDeviceResources = devices.stream().map(device -> {
			return build(device);
		}).collect(Collectors.toList());

		Resources<Resource<DeviceToJsonAdapter>> deviceResources = new Resources<>(transformedDeviceResources);
		deviceResources.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(DeviceResource.class).all()).withSelfRel());
		return deviceResources;
	}
}
