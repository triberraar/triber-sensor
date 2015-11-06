package be.tribersoft.sensor.domain.impl.device;

import java.util.Date;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import be.tribersoft.common.DateFactory;
import be.tribersoft.sensor.domain.api.device.Device;

@Entity(name = "device")
public class DeviceEntity implements Device {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "id", unique = true, nullable = false)
	private String id;

	@Version
	@Column(nullable = false)
	private Long version;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	@Column(nullable = false, length = 256)
	private String name;

	@Column(length = 4098)
	private String description;

	@Column(length = 256)
	private String location;

	DeviceEntity() {
	}

	public DeviceEntity(String name) {
		this.name = name;
		this.creationDate = DateFactory.generate();
	}

	public void setDescription(Optional<String> description) {
		if (description.isPresent()) {
			this.description = description.get();
		} else {
			this.description = null;
		}
	}

	public void setLocation(Optional<String> location) {
		if (location.isPresent()) {
			this.location = location.get();
		} else {
			this.location = null;
		}
	}

	@Override
	public String getName() {
		return name;
	}

	Date getCreationDate() {
		return creationDate;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public Long getVersion() {
		return version;
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.ofNullable(description);
	}

	public void setName(String name) {
		this.name = name;

	}

	@Override
	public Optional<String> getLocation() {
		return Optional.ofNullable(location);
	}
}
